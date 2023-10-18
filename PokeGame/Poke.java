package PokeGame;

import java.util.ArrayList;
import java.util.Collections;
// import java.util.stream.Stream;

//
public class Poke {
    // 定义常量牌（花色，点数）
    private static String[] pokecard = { "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A", "2", "SMALL",
            "BIG" };
    private static String[] pokecolor = { "黑桃", "红心", "梅花", "方块" };

    private Poke() {

    }

    // 返回牌
    public String[] getPokecard() {
        return pokecard;
    }

    // 返回花色
    public String[] getPockcolor() {
        return pokecolor;
    }

    // 生成一副完整的牌
    public static ArrayList<String> makePoke() {
        ArrayList<String> poke = new ArrayList<>();
        for (int i = 0; i < pokecard.length - 2; i++) {
            for (int j = 0; j < pokecolor.length; j++) {
                Collections.addAll(poke, pokecard[i] + pokecolor[j]);
            }
        }
        // 添加大小王
        poke.add(pokecard[pokecard.length - 2]);
        poke.add(pokecard[pokecard.length - 1]);
        return poke;
    }

}