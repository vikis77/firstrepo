package PokeGame;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

//import PokeGame.fightlandlord.MyComparator2;

import java.util.Iterator;
import java.util.List;

public class Person {
    private ArrayList<String> poke = new ArrayList<>();// 个人玩家手牌
    private String name;// 玩家名字
    private int landlord_times = 0;// 个人个人抢地主次数
    private Boolean islandlord = false;// 是否地址

    public Person() {

    }

    public Person(String name) {// 返回人名
        this.name = name;
    }

    public String get_name() {// 设置人名
        return this.name;
    }

    public int get_landlord_times() {// 获取抢地主次数
        return landlord_times;
    }

    public ArrayList<String> get_Poke() {// 返回一个玩家拥有的牌
        return poke;
    }

    public void setPoke(ArrayList<String> poke) {// 设置一个玩家的牌为。。
        this.poke = poke;
    }

    public boolean Isislandlord() {// 返回玩家是否地主
        return islandlord;
    }

    public void setIslandlord(boolean islandlord) {// 设置是否地主
        this.islandlord = islandlord;
    }

    // 抢地主
    public void Person_changelandlord() {
        while (true) {
            Scanner input = new Scanner(System.in);
            String t1;
            System.out.println(this.name.replaceAll(" ", "") + ",是否抢地主？\n抢 ：Y或y\n不抢：N或n");
            t1 = input.next();
            if ((t1.equals("Y")) || (t1.equals("y"))) {
                this.landlord_times++;// 个人抢地主次数加1
                if (this.landlord_times == 2) {// 个人抢地主两次
                    this.islandlord = true;// 成为地主
                    System.out.println(this.name.replaceAll(" ", "") + "是地主");// 打印地主
                    // input.close();
                    break;
                } else {
                    // input.close();
                    break;
                }
            } else if (t1.equals("n") || t1.equals("N")) {
                break;
            } else {
                System.out.println("你就说你要不要嘛！快点儿~~");// 输入非法，重新输入
                continue;
            }
        }
    }

    // 玩家出牌
    public void sendpoke() {
        while (true) {
            String temp_str = "";
            String usersc = "";// 记录输入变量
            String[] spoke = null;// 记录输入用，分割后的字符串数组
            String next;
            int sendpoke_legal;
            int i = 0;// 循环记号
            ArrayList<String> list = new ArrayList<>();// 复制一份手牌
            ArrayList<String> list2 = new ArrayList<>();// 出的牌

            // list手牌赋值
            for (i = 0; i < poke.size(); i++) {
                list.add(poke.get(i));
            }

            // 出牌输入
            System.out.println("\n" + this.name.replaceAll(" ", "") + "请输入您要出的牌(不用输入花色,出多张牌用,分开  要不起或不要请输入N):");
            System.out.println(poke);// 打印玩家拥有的牌
            Scanner scanner = new Scanner(System.in);// 定义一个扫描器对象
            usersc = scanner.next();// 输入要出的牌
            if (fightlandlord.new_poke == "" && (usersc.equals("N") || usersc.equals("n"))) {// 无出牌前后记录时，该玩家必须出牌
                System.out.println("想啥呢，该你出牌了!");
                continue;
            }
            if (fightlandlord.new_poke != "" && (usersc.equals("N") || usersc.equals("n"))) {// 是否输入为 要不起
                // fightlandlord.coiled_time = fightlandlord.coiled_time + 1;//记录 连续 要不起次数
                System.out.println(this.name + "要不起");
                fightlandlord.set_Times_of_refuse();// 记录要不起次数
                System.out.println("new_poke " + fightlandlord.new_poke);
                System.out.println("old_poke " + fightlandlord.old_poke);
                // scanner.close();
                return;
            }

            spoke = usersc.split(",");// 分割

            // 将输入的Srtring转场Arraylist进行排序，再转回字符串
            for (i = 0; i < spoke.length; i++) {
                list2.add(spoke[i]);
            }
            Collections.sort(list2, new MyComparator());// 使用重写比较器按照大到小排序
            spoke = list2.toArray(new String[list2.size()]);

            // 定义一个迭代器对象
            Iterator<String> iterator = list.iterator();
            // 出的牌与手牌匹配
            i = 0;// 重置标记i
            while (iterator.hasNext() && i < spoke.length) {
                next = iterator.next();// 拿到手牌迭代器中第一张牌
                if (next.contains(spoke[i])) {// 以出的牌为主，看手牌中有没有要出的牌，i也表示了成功匹配的个数
                    temp_str = temp_str + next;// 出牌记录
                    iterator.remove();// 匹配成功后移除迭代器中该牌
                    i++;
                }
            }

            // 出牌成功
            if (i == spoke.length) {
                sendpoke_legal = fightlandlord.judge_the_legality_of_the_deal(temp_str);// 传出出牌判断是否合法，包括类型合法和大小合法，合法返回1，非法返回0，王炸特殊情况返回2
                if (sendpoke_legal == 0) {// 如果出牌不合法
                    System.out.println("重新出牌吧你！");
                    continue;// 相当于重新调用sendpoke()出牌函数
                }
                // 出牌合法
                this.poke = list;// 更新手牌
                fightlandlord.times_of_redfuse = 0;// 出牌合法后，要不起次数清零，确保要不起次数连续性
                System.out.println("出牌成功！" + this.name.replaceAll(" ", "") + "您现在的手牌是：" + poke);

                // 更新完手牌后，王炸可以再出一次牌
                if (sendpoke_legal == 2) {
                    continue;// 再次出牌
                }
                return;
            }
            // 出牌失败 =>手牌中没有要出的牌 ,即与手牌匹配成功的个数小于要出的牌的个数
            if (i < spoke.length) {
                System.out.println("你没有该牌，别给我浑水摸鱼！");
                System.out.println("重新出牌吧你！");
                continue;// 重新出牌
            }
        }
    }

    // 重构比较器，用于手牌从大到小排序 用于sendpoke()函数中Arraylist类型的list2排序
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
}
