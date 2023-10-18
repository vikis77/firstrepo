package PokeGame;

import java.util.ArrayList;

public class main {
    public static void main(String[] args) {

        Person p1 = new Person("王 公 子");
        Person p2 = new Person("赵    总");
        Person p3 = new Person("小秦同学");
        ArrayList<String> Pokes = new ArrayList<>();// 定义一个整幅牌对象
        Pokes = Poke.makePoke();// 生成一副牌
        System.out.println("洗牌前：" + Pokes + "\n");
        fightlandlord FLL = new fightlandlord(Pokes, p1, p2, p3);// 定义一个斗地主
        FLL.shufflecards();// 洗牌
        System.out.println("洗牌后：" + Pokes + "\n");
        FLL.Licensing();// 发牌
        FLL.print_person_poke();// 打印手牌
        FLL.changelandlord();// 抢地主
        FLL.Licensing_landlord();// 发地主牌
        FLL.print_person_poke();// 打印手牌
        FLL.startPoke();// 开始打牌
    }
}
