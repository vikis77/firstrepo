package PokeGame;

import java.lang.foreign.ValueLayout.OfLong;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
// import java.util.Random;
// import java.util.Scanner;
import java.util.HashMap;
import java.util.Map;

public class fightlandlord {
    private static String REGEX_CHINESE = "[\u4e00-\u9fa5]";// 中文正则
    int total_chargelandlord_time = 0;// 记录总计抢地主次数
    int Number_of_turns = 0;// 记录圈数，三次（人）为一圈
    // public static int coiled_time = 0;
    public static int times_of_redfuse = 0;// 记录出牌要不起次数
    public static String new_poke = "";// 记录后（新出的）牌
    public static String old_poke = "";// 记录前（先出的）牌
    public Person p1;
    public Person p2;
    public Person p3;
    public ArrayList<String> poke;// 定义一整幅牌

    public fightlandlord() {

    }

    // 斗地主构造函数
    public fightlandlord(ArrayList<String> poke, Person p1, Person p2, Person p3) {
        this.poke = poke;
        this.p1 = p1;
        this.p2 = p2;
        this.p3 = p3;
    }

    // 洗牌，即打乱牌
    public ArrayList<String> shufflecards() {
        Collections.shuffle(poke);
        return poke;
    }

    // 发牌
    public void Licensing() {
        for (int i = 0; i < poke.size() - 3; i += 3) {
            p1.get_Poke().add(poke.get(i));
            p2.get_Poke().add(poke.get(i + 1));
            p3.get_Poke().add(poke.get(i + 2));
        }
    }

    // 发地主牌
    public void Licensing_landlord() {
        if (p1.Isislandlord()) {
            for (int i = 51; i < poke.size(); i++) {
                System.out.println("地主牌为：" + poke.get(i));
                p1.get_Poke().add(poke.get(i));
            }
        }
        if (p2.Isislandlord()) {
            for (int i = 51; i < poke.size(); i++) {
                System.out.println("地主牌为：" + poke.get(i));
                p2.get_Poke().add(poke.get(i));
            }
        }
        if (p3.Isislandlord()) {
            for (int i = 51; i < poke.size(); i++) {
                System.out.println("地主牌为：" + poke.get(i));
                p3.get_Poke().add(poke.get(i));
            }
        }

    }

    // 按从大到小顺序排序手牌
    public void sort_Poke() {
        Collections.sort(p1.get_Poke(), new MyComparator());
        Collections.sort(p2.get_Poke(), new MyComparator());
        Collections.sort(p3.get_Poke(), new MyComparator());
        // Collections.sort(p3.get_Poke(), new MyComparator2());
    }

    // 打印全部玩家手牌
    public void print_person_poke() {
        sort_Poke();// 排序
        System.out.println(p1.get_name() + "的手牌：" + p1.get_Poke());
        System.out.println(p2.get_name() + "的手牌：" + p2.get_Poke());
        System.out.println(p3.get_name() + "的手牌：" + p3.get_Poke());
    }

    // 判断出牌是否合法 包括类型合法和大小合法，合法返回1，非法返回0，王炸特殊情况返回2
    public static int judge_the_legality_of_the_deal(String pre_pokeE) {
        int size_relatively_legal;// 同意执行,记录出牌是否符合大小合法性,合法为1
        String Tpoke_type;// 牌型变量
        String oo = old_poke;// 用于非法回溯 出牌前后记录
        String nn = new_poke;

        pre_pokeE = removeChinese_kingto_WV(pre_pokeE);// 将传出的手牌进行转换
        Tpoke_type = playing_poke_type(pre_pokeE);// 判断牌型

        if (Tpoke_type != "poke_rule_error") {// 牌型合法
            // 牌型为 王炸
            if (Tpoke_type == "王炸") {
                old_poke = "";// 王炸后出牌出牌记录清零,返回2
                new_poke = "";
                System.out.println("new_poke " + new_poke);
                System.out.println("old_poke " + old_poke);
                return 2;
            }

            // 无出牌记录时
            if (new_poke.length() == 0) {
                System.out.println("出牌合法");
                new_poke = pre_pokeE;// 记录
                System.out.println("new_poke " + new_poke);
                System.out.println("old_poke " + old_poke);
                return 1;
            }
            // 有出牌记录时
            else {
                old_poke = new_poke;// 更新出牌前后记录
                new_poke = pre_pokeE;
                size_relatively_legal = than_size(new_poke, old_poke);// 比较出牌大小合法性，合法返回1，非法返回0
                if (size_relatively_legal == 1) {
                    System.out.println("出牌合法");
                    System.out.println("new_poke " + new_poke);
                    System.out.println("old_poke " + old_poke);
                    return 1;
                } else {
                    System.out.println("出牌大小非法");
                    old_poke = oo;// 回溯出牌记录
                    new_poke = nn;
                    System.out.println("new_poke " + new_poke);
                    System.out.println("old_poke " + old_poke);
                    return 0;
                }
            }
        } else {// 牌型非法
            System.out.println("出牌牌型非法");
            System.out.println("new_poke" + new_poke);
            System.out.println("old_poke" + old_poke);
            return 0;
        }
    }

    // 记录要不起次数，超过两次清零，清空判断大小出牌记录
    public static void set_Times_of_refuse() {
        times_of_redfuse++;
        if (times_of_redfuse > 1) {
            times_of_redfuse = 0;
            old_poke = "";
            new_poke = "";
        }
    }

    // （将字符串类型的牌 大小王替换成 O N，）&& 字符串去中文 &&去逗号
    public static String removeChinese_kingto_WV(String str) {
        String strr;

        if (str.contains("B")) {
            str = str.replace("BIG", "O");
        }
        if (str.contains("S")) {
            str = str.replace("SMALL", "N");
        }
        if (str.contains("2")) {
            str = str.replace("2", "M");
        }
        if (str.contains("A")) {
            str = str.replace("A", "L");
        }
        if (str.contains("J")) {
            str = str.replace("J", "I");
        }
        if (str.contains("Q")) {
            str = str.replace("Q", "J");
        }
        if (str.contains("10")) {
            str = str.replace("10", "H");
        }
        if (str.contains("9")) {
            str = str.replace("9", "G");
        }
        if (str.contains("8")) {
            str = str.replace("8", "F");
        }
        if (str.contains("7")) {
            str = str.replace("7", "E");
        }
        if (str.contains("6")) {
            str = str.replace("6", "D");
        }
        if (str.contains("5")) {
            str = str.replace("5", "C");
        }
        if (str.contains("4")) {
            str = str.replace("4", "B");
        }
        if (str.contains("3")) {
            str = str.replace("3", "A");
        }
        strr = str.replaceAll(",", "");
        strr = str.replaceAll(REGEX_CHINESE, "");
        return strr;
    }

    public void changelandlord() {// 抢地主
        java.util.Random random = new java.util.Random();// 随机对象，定第一个人是谁
        int Random_num1 = random.nextInt(0, 2);// 随机0-2，三个人
        int cp_Random_num1 = Random_num1;// 复制一份
        while (Number_of_turns < 3) {
            // 第一圈（三个人轮一遍）
            if (Random_num1 == 0 && Number_of_turns < 3) {
                Number_of_turns++;// 轮过一个 加一
                p1.Person_changelandlord();// 是否抢地主
                Random_num1++;// 加一 轮第二个人
            }
            if (Random_num1 == 1 && Number_of_turns < 3) {
                Number_of_turns++;
                p2.Person_changelandlord();
                Random_num1++;// 加一 轮第三个人
            }
            if (Random_num1 == 2 && Number_of_turns < 3) {
                Number_of_turns++;
                p3.Person_changelandlord();
                Random_num1 = 0;// 加一 轮回第一个人
            }
            if (Number_of_turns == 3) {// 轮了一遍过后
                int time_per_oneturn = p1.get_landlord_times() + p2.get_landlord_times() + p3.get_landlord_times();// 一轮中有几个人叫地主
                                                                                                                   // 0-3
                switch (time_per_oneturn) {
                    case 0:// 0个人叫地主
                        switch (cp_Random_num1) {// 随机出来的第一个人为地主
                            case 0:
                                p1.setIslandlord(true);
                                System.out.println(p1.get_name() + "是地主");
                                break;
                            case 1:
                                p1.setIslandlord(true);
                                System.out.println(p2.get_name() + "是地主");
                                break;
                            case 2:
                                p1.setIslandlord(true);
                                System.out.println(p3.get_name() + "是地主");
                                break;
                        }
                    case 1:// 1个人叫地主，谁叫就谁地主
                        if (p1.get_landlord_times() == 1) {
                            p1.setIslandlord(true);
                            System.out.println(p1.get_name() + "是地主");
                            break;
                        }
                        if (p2.get_landlord_times() == 1) {
                            p2.setIslandlord(true);
                            System.out.println(p2.get_name() + "是地主");
                            break;
                        } else {
                            p3.setIslandlord(true);
                            System.out.println(p3.get_name() + "是地主");
                            break;
                        }
                    case 2:// 2个人叫地主
                        if (Random_num1 == 0) {// 接着轮下一个人
                            // Random_num1++;
                            if (p1.get_landlord_times() != 0) {// p1第一圈有叫地主
                                p1.Person_changelandlord();// 还要不要叫地主
                                if (p1.Isislandlord() == false) {// 不叫地主了
                                    if (p2.get_landlord_times() != 0) {// 判断还有谁第一圈叫了地主，此行表示 p2第一圈叫了
                                        p2.setIslandlord(true);// ，p2为地主
                                        System.out.println(p2.get_name() + "是地主");
                                        break;
                                    } else {// 否则p3为地主
                                        p3.setIslandlord(true);
                                        System.out.println(p3.get_name() + "是地主");
                                        break;
                                    }
                                }
                                break;
                            }
                        }
                        if (Random_num1 == 1) {
                            // Random_num1++;
                            if (p2.get_landlord_times() != 0) {
                                p2.Person_changelandlord();
                                if (p2.Isislandlord() == false) {
                                    if (p1.get_landlord_times() != 0) {
                                        p1.setIslandlord(true);
                                        System.out.println(p1.get_name() + "是地主");
                                        break;
                                    } else {
                                        p3.setIslandlord(true);
                                        System.out.println(p3.get_name() + "是地主");
                                        break;
                                    }
                                }
                                break;
                            }
                        }
                        if (Random_num1 == 2) {
                            // Random_num1++;
                            if (p3.get_landlord_times() != 0) {
                                p3.Person_changelandlord();
                                if (p3.Isislandlord() == false) {
                                    if (p1.get_landlord_times() != 0) {
                                        p1.setIslandlord(true);
                                        System.out.println(p1.get_name() + "是地主");
                                        break;
                                    } else {
                                        p3.setIslandlord(true);
                                        System.out.println(p2.get_name() + "是地主");
                                        break;
                                    }
                                }
                                break;
                            }
                        }
                        break;
                    case 3:// 3个人叫地主，最后叫的为地主
                        switch (Random_num1) {
                            case 0:
                                p3.setIslandlord(true);
                                System.out.println(p3.get_name() + "是地主");
                                break;
                            case 1:
                                p1.setIslandlord(true);
                                System.out.println(p1.get_name() + "是地主");
                                break;
                            case 2:
                                p2.setIslandlord(true);
                                System.out.println(p2.get_name() + "是地主");
                                break;
                        }
                        break;

                }
            }
        }

    }

    // 开始打牌
    public void startPoke() {
        if (p1.Isislandlord()) {
            while (true) {
                p1.sendpoke();
                if (p1.get_Poke().isEmpty()) {
                    System.out.println("地主获胜");
                    return;
                }
                p2.sendpoke();
                if (p2.get_Poke().isEmpty()) {
                    System.out.println("农民获胜");
                    return;
                }
                p3.sendpoke();
                if (p3.get_Poke().isEmpty()) {
                    System.out.println("农民获胜");
                    return;
                }
            }
        }
        if (p2.Isislandlord()) {
            while (true) {
                p2.sendpoke();
                if (p2.get_Poke().isEmpty()) {
                    System.out.println("地主获胜");
                    return;
                }
                p3.sendpoke();
                if (p3.get_Poke().isEmpty()) {
                    System.out.println("农民获胜");
                    return;
                }
                p1.sendpoke();
                if (p1.get_Poke().isEmpty()) {
                    System.out.println("农民获胜");
                    return;
                }
            }

        }
        if (p3.Isislandlord()) {
            while (true) {
                p3.sendpoke();
                if (p3.get_Poke().isEmpty()) {
                    System.out.println("地主获胜");
                    return;
                }
                p1.sendpoke();
                if (p1.get_Poke().isEmpty()) {
                    System.out.println("农民获胜");
                    return;
                }
                p2.sendpoke();
                if (p2.get_Poke().isEmpty()) {
                    System.out.println("农民获胜");
                    return;
                }
            }

        }
    }

    // 重构比较器，用于手牌从大到小排序
    public class MyComparator implements Comparator<String> {
        @Override
        public int compare(String o1, String o2) {
            char m = 0, n = 0;
            if (o1.charAt(0) == 'B') {
                m = 'A';
            } else if (o1.charAt(0) == 'S') {
                m = '@';
            } else if (o1.charAt(0) == '2') {
                m = '?';
            } else if (o1.charAt(0) == 'A') {
                m = '>';
            } else if (o1.charAt(0) == 'K') {
                m = '=';
            } else if (o1.charAt(0) == 'Q') {
                m = '<';
            } else if (o1.charAt(0) == 'J') {
                m = ';';
            } else if (o1.charAt(0) == '1') {
                m = ':';
            } else {
                m = o1.charAt(0);
            }
            if (o2.charAt(0) == 'B') {
                n = 'A';
            } else if (o2.charAt(0) == 'S') {
                n = '@';
            } else if (o2.charAt(0) == '2') {
                n = '?';
            } else if (o2.charAt(0) == 'A') {
                n = '>';
            } else if (o2.charAt(0) == 'K') {
                n = '=';
            } else if (o2.charAt(0) == 'Q') {
                n = '<';
            } else if (o2.charAt(0) == 'J') {
                n = ';';
            } else if (o2.charAt(0) == '1') {
                n = ':';
            } else {
                n = o2.charAt(0);
            }
            return n - m;
        }
    }

    // 判断牌型 传入形参已转换并排好序（降序）
    public static String playing_poke_type(String old_poke) {
        System.out.println(old_poke);
        String type_poke = "";// 牌型

        // 用于判断是否为顺子
        int flag = 1;// 大小连续牌数
        for (int j = 0; j < old_poke.length() - 1; j++) {
            if (old_poke.charAt(j) - 1 == old_poke.charAt(j + 1) - 0) {
                flag++;
            }
        }

        // 字典 ACBCDE个数型
        Map<String, Integer> dict_Brand_acb = new HashMap<String, Integer>();
        dict_Brand_acb.put("A", 0);
        dict_Brand_acb.put("B", 0);
        dict_Brand_acb.put("C", 0);
        dict_Brand_acb.put("D", 0);
        dict_Brand_acb.put("E", 0);
        // 从前面扫描到后面
        int i = 0;
        if (i < old_poke.length()) {// 先记下一次
            dict_Brand_acb.put("A", dict_Brand_acb.get("A") + 1);
        }
        while (i < old_poke.length() - 1 && old_poke.charAt(i) == old_poke.charAt(i + 1)) {// 与下一个相同时，再计一个
            i++;
            dict_Brand_acb.put("A", dict_Brand_acb.get("A") + 1);
        }
        i++;

        if (i < old_poke.length()) {
            dict_Brand_acb.put("B", dict_Brand_acb.get("B") + 1);
        }
        while (i < old_poke.length() - 1 && old_poke.charAt(i) == old_poke.charAt(i + 1)) {
            i++;
            dict_Brand_acb.put("B", dict_Brand_acb.get("B") + 1);
        }
        i++;

        if (i < old_poke.length()) {
            dict_Brand_acb.put("C", dict_Brand_acb.get("C") + 1);
        }
        while (i < old_poke.length() - 1 && old_poke.charAt(i) == old_poke.charAt(i + 1)) {
            i++;
            dict_Brand_acb.put("C", dict_Brand_acb.get("C") + 1);
        }
        i++;

        if (i < old_poke.length()) {
            dict_Brand_acb.put("D", dict_Brand_acb.get("D") + 1);
        }
        while (i < old_poke.length() - 1 && old_poke.charAt(i) == old_poke.charAt(i + 1)) {
            i++;
            dict_Brand_acb.put("D", dict_Brand_acb.get("D") + 1);
        }
        i++;

        if (i < old_poke.length()) {
            dict_Brand_acb.put("E", dict_Brand_acb.get("E") + 1);
        }
        while (i < old_poke.length() - 1 && old_poke.charAt(i) == old_poke.charAt(i + 1)) {
            i++;
            dict_Brand_acb.put("E", dict_Brand_acb.get("E") + 1);
        }

        // 对字典数据A到E降序排序，确保A的值最大
        int[] temp_dic_str = new int[5];
        temp_dic_str[0] = dict_Brand_acb.get("A");
        temp_dic_str[1] = dict_Brand_acb.get("B");
        temp_dic_str[2] = dict_Brand_acb.get("C");
        temp_dic_str[3] = dict_Brand_acb.get("D");
        temp_dic_str[4] = dict_Brand_acb.get("E");
        Arrays.sort(temp_dic_str, 0, 5);// 升序排列
        dict_Brand_acb.put("A", temp_dic_str[4]);// 按个数重大到小排序，避免出现33344识别为44333
        dict_Brand_acb.put("B", temp_dic_str[3]);
        dict_Brand_acb.put("C", temp_dic_str[2]);
        dict_Brand_acb.put("D", temp_dic_str[1]);
        dict_Brand_acb.put("E", temp_dic_str[0]);

        // System.out.println(dict_Brand_acb.get("A"));
        // System.out.println(dict_Brand_acb.get("B"));
        // System.out.println(dict_Brand_acb.get("C"));
        // System.out.println(dict_Brand_acb.get("D"));
        // System.out.println(dict_Brand_acb.get("E"));

        // 用字典ABCDE判断出牌类型//
        // 王炸
        if (dict_Brand_acb.get("A") == 1 && dict_Brand_acb.get("B") == 1 && dict_Brand_acb.get("C") == 0
                && dict_Brand_acb.get("D") == 0 && dict_Brand_acb.get("E") == 0) {
            if (old_poke.contains("O") && old_poke.contains("N") && (old_poke.length() == 2)) {
                System.out.println("王炸");
                type_poke = "王炸";
                return type_poke;
            } else {
                type_poke = "poke_rule_error";
                return type_poke;
            }
        }
        // 炸弹
        else if (dict_Brand_acb.get("A") == 4 && dict_Brand_acb.get("B") == 0 && dict_Brand_acb.get("C") == 0
                && dict_Brand_acb.get("D") == 0 && dict_Brand_acb.get("E") == 0) {
            System.out.println("炸弹");
            type_poke = "炸弹";
            return type_poke;
        }
        // 五连对
        else if (dict_Brand_acb.get("A") == 2 && dict_Brand_acb.get("B") == 2 && dict_Brand_acb.get("C") == 2
                && dict_Brand_acb.get("D") == 2 && dict_Brand_acb.get("E") == 2) {
            if (old_poke.charAt(0) != 'O' || old_poke.charAt(0) != 'N') {// 不包含king、2
                System.out.println("五连对");
                type_poke = "五连对";
                return type_poke;
            } else {
                type_poke = "poke_rule_error";
                return type_poke;
            }
        }
        // 四连对
        else if (dict_Brand_acb.get("A") == 2 && dict_Brand_acb.get("B") == 2 && dict_Brand_acb.get("C") == 2
                && dict_Brand_acb.get("D") == 2 && dict_Brand_acb.get("E") == 0) {
            if (old_poke.charAt(0) != 'O' || old_poke.charAt(0) != 'N') {// 不包含king、2
                System.out.println("四连对");
                type_poke = "四连对";
                return type_poke;
            } else {
                type_poke = "poke_rule_error";
                return type_poke;
            }
        }
        // 三连对
        else if (dict_Brand_acb.get("A") == 2 && dict_Brand_acb.get("B") == 2 && dict_Brand_acb.get("C") == 2
                && dict_Brand_acb.get("D") == 0 && dict_Brand_acb.get("E") == 0) {
            if (old_poke.charAt(0) != 'O' || old_poke.charAt(0) != 'N') {// 不包含king、2
                System.out.println("三连对");
                type_poke = "三连对";
                return type_poke;
            } else {
                type_poke = "poke_rule_error";
                return type_poke;
            }
        }
        // 飞机3带2对
        else if ((dict_Brand_acb.get("A") == 3 && dict_Brand_acb.get("B") == 3 && dict_Brand_acb.get("C") == 2
                && dict_Brand_acb.get("D") == 2 && dict_Brand_acb.get("E") == 0)) {
            for (int j = 0; j < old_poke.length() - 1; j++) {// 如果包含2，返回错误
                if (old_poke.charAt(j) == '2') {
                    type_poke = "poke_rule_error";
                    return type_poke;
                }
            }
            System.out.println("飞机3带2对");
            type_poke = "飞机3带2对";
            return type_poke;
        }
        // 飞机3带2个
        else if (dict_Brand_acb.get("A") == 3 && dict_Brand_acb.get("B") == 3 && dict_Brand_acb.get("C") == 1
                && dict_Brand_acb.get("D") == 1 && dict_Brand_acb.get("E") == 0) {
            for (int j = 0; j < old_poke.length() - 1; j++) {// 如果包含2，返回错误
                if (old_poke.charAt(i) == '2') {
                    type_poke = "poke_rule_error";
                    return type_poke;
                }
            }
            System.out.println("飞机3带2个");
            type_poke = "飞机3带2个";
            return type_poke;
        }
        // 飞机没翅膀
        else if (dict_Brand_acb.get("A") == 3 && dict_Brand_acb.get("B") == 3 && dict_Brand_acb.get("C") == 0
                && dict_Brand_acb.get("D") == 0 && dict_Brand_acb.get("E") == 0) {
            for (int j = 0; j < old_poke.length() - 1; j++) {// 如果包含2，返回错误
                if (old_poke.charAt(i) == '2') {
                    type_poke = "poke_rule_error";
                    return type_poke;
                }
            }
            System.out.println("飞机没翅膀");
            type_poke = "飞机没翅膀";
            return type_poke;

        } // 4带2对
        else if (dict_Brand_acb.get("A") == 4 && dict_Brand_acb.get("B") == 2 && dict_Brand_acb.get("C") == 2
                && dict_Brand_acb.get("D") == 0 && dict_Brand_acb.get("E") == 0) {
            System.out.println("4带2对");
            type_poke = "4带2对";
            return type_poke;
        }
        // 4带2个
        else if (dict_Brand_acb.get("A") == 4 && dict_Brand_acb.get("B") == 1 && dict_Brand_acb.get("C") == 1
                && dict_Brand_acb.get("D") == 0 && dict_Brand_acb.get("E") == 0) {
            System.out.println("4带2个");
            type_poke = "4带2个";
            return type_poke;
        }
        // 3带2个
        else if (dict_Brand_acb.get("A") == 3 && dict_Brand_acb.get("B") == 2 && dict_Brand_acb.get("C") == 0
                && dict_Brand_acb.get("D") == 0 && dict_Brand_acb.get("E") == 0) {
            System.out.println("3带2个");
            type_poke = "3带2个";
            return type_poke;
        }
        // 3带1个
        else if (dict_Brand_acb.get("A") == 3 && dict_Brand_acb.get("B") == 1 && dict_Brand_acb.get("C") == 0
                && dict_Brand_acb.get("D") == 0 && dict_Brand_acb.get("E") == 0) {
            System.out.println("3带1个");
            type_poke = "3带1个";
            return type_poke;
        }
        // 3个不带
        else if (dict_Brand_acb.get("A") == 3 && dict_Brand_acb.get("B") == 0 && dict_Brand_acb.get("C") == 0
                && dict_Brand_acb.get("D") == 0 && dict_Brand_acb.get("E") == 0) {
            System.out.println("3个不带");
            type_poke = "3个不带";
            return type_poke;
        }
        // 一对
        else if (dict_Brand_acb.get("A") == 2 && dict_Brand_acb.get("B") == 0 && dict_Brand_acb.get("C") == 0
                && dict_Brand_acb.get("D") == 0 && dict_Brand_acb.get("E") == 0) {
            System.out.println("一对");
            type_poke = "一对";
            return type_poke;
        }
        // 单牌
        else if (dict_Brand_acb.get("A") == 1 && dict_Brand_acb.get("B") == 0 && dict_Brand_acb.get("C") == 0
                && dict_Brand_acb.get("D") == 0 && dict_Brand_acb.get("E") == 0) {
            System.out.println("单牌");
            type_poke = "单牌";
            return type_poke;
        } else if (flag == old_poke.length()) {// 为顺子
            System.out.println("顺子");
            return "顺子";
        } else {
            type_poke = "poke_rule_error";
            return type_poke;
        }
    }

    // 出牌大小是否合法，合法返回1，不合法返回-1（包含规则）
    public static int than_size(String new_poke, String old_poke) {
        String new_type = playing_poke_type(new_poke);// 判断排序
        String old_type = playing_poke_type(old_poke);

        if (new_type.equals(old_type) && new_type == "顺子") {
            if (new_poke.charAt(0) != 'M' && new_poke.charAt(0) > old_poke.charAt(0)) {// 不包含2，新牌顺子大，返回1
                return 1;
            } else {
                return 0;
            }
        } else if (new_type == "王炸") {// 清空比大小出牌记录
            fightlandlord.old_poke = "";
            fightlandlord.new_poke = "";
            return 1;
        } else if (new_type == "炸弹") {
            if (old_poke == "王炸") {
                return 0;
            } else {
                return 1;
            }
        } else if (new_type.equals(old_type) && (new_type == "五连对" || new_type == "四连对" || new_type == "三连对"
                || new_type == "飞机3带2对" || new_type == "飞机3带2个" || new_type == "飞机没翅膀")) {
            if (new_poke.charAt(0) != 'M' && (new_poke.charAt(0) > old_poke.charAt(0))) {
                return 1;
            } else {
                return 0;
            }
        } else if (new_type.equals(old_type) && (new_type == "4带2对" || new_type == "4带2个" || new_type == "3带2个"
                || new_type == "3带1个" || new_type == "3个不带" || new_type == "一对" || new_type == "单牌")) {
            if ((new_poke.charAt(0) > old_poke.charAt(0))) {
                return 1;
            } else {
                return 0;
            }
        } else {
            return 0;
        }
    }
}