package com.xhy.reload.news.utils;

import com.xhy.reload.news.R;

import java.util.Random;

public class Utils {
    private static int[] imgSrc = {R.drawable.a,R.drawable.b,R.drawable.c,R.drawable.d,R.drawable.e,R.drawable.f,
            R.drawable.g,R.drawable.h,R.drawable.i,R.drawable.j,R.drawable.k,R.drawable.l,
            R.drawable.m,R.drawable.n,R.drawable.o,R.drawable.p,R.drawable.q,R.drawable.r,
            R.drawable.s,R.drawable.t};

    private static String[] addrs = {"香港特别行政区","广东南宁市","福建省","浙江省杭州市","河北省廊坊市","安徽省六安市",
            "四川省成都市","山西省太原市","北京市","山东省威海","安徽省阜阳市","河南省洛阳市",
            "江苏省南京市","福建省宁德市","天津市","江苏省南通","河北省石家庄市","广东省东莞",
            "贵州省安顺市","湖北省武汉市","上海市黄浦区","吉林省吉林市","西藏拉萨","黑龙江齐齐哈尔市"};

    private static String[] phoneType = {"iphone 7","iphone 6","iphone x","小米Note",
            "小米MIX2","华为手机","魅族手机","LG手机","HTC手机","三星S9+","诺基亚手机","vivo NEX",
            "iphone SE","samsung s8","vivo Xplay 5","努比亚手机","华为荣耀7i","华为Mate8","oppo R11s"};

    private static String[] authors = {"紫月如钩","火星网友","据说有利","AMD首席工程师","吾名字被占用了",
            "打酱油","寒夜冰封","喵星人保护协会","后学术士","影魔","鲁班7号","串串岁月","他一只猫",
            "石头小青年","背包客","说走就走","因为我爱的人在远方","呵呵哒","不归少年","官方发言人",
            "村夫老墅","包青天大人","你是不是饿得慌","微软小爆","光着膀子加油干","名字太长显示不出来...",
            "有态度的人","昔日午后","向日葵","亚丁","叫大爷","怎么个态度","老庙一座","哈佛博士研究僧",
            "hi叫你呢","活着","逆水寒","被眼睛迷惑了","王路飞","社会我衣哥"};

    private static String[] comContent = {"山有木兮木有枝，心悦君兮君不知。","人生若只如初见，何事秋风悲画扇。",
            "十年生死两茫茫，不思量，自难忘。","曾经沧海难为水，除却巫山不是云。",
            "玲珑骰子安红豆，入骨相思知不知。","今人不见古时月，今月曾经照古人。",
            "二十四桥明月夜，玉人何处教吹箫？","春江潮水连海平，海上明月共潮生。",
            "雨打梨花深闭门，忘了青春，误了青春。","空山新雨后，天气晚来秋。",
            "天街小雨润如酥，草色遥看近却无。","七八个星天外，两三点雨山前。",
            "空床卧听南窗雨，谁复挑灯夜补衣","闲梦江南梅熟日，夜船吹笛雨萧萧。",
            "少年不识愁滋味，爱上层楼。爱上层楼。为赋新词强说愁。",
            "一生大笑能几回，斗酒相逢须醉倒。","孤舟蓑笠翁，独钓寒江",
            "江碧鸟逾白，山青花欲燃。","无边落木萧萧下，不尽长江滚滚来。",
            "人生如逆旅，我亦是行人。","月有盈亏花有开谢，想人生最苦离别。",
            "时人不识凌云木，直待凌云始道高。","沉舟侧畔千帆过，病树前头万木春。",
            "山重水复疑无路，柳暗花明又一村。","不识庐山真面目，只缘身在此山中。",
            "不畏浮云遮望眼，只缘身在最高层。","草木有本心，何求美人折！",
            "世间行乐亦如此，古来万事东流水。","天生我材必有用，千金散尽还复来。",
            "黄沙百战穿金甲，不破楼兰终不还。","男儿何不带吴钩，收取关山五十州。",
            "长风破浪会有时，直挂云帆济沧海。","纸上得来终觉浅，绝知此事要躬行。",
            "读书不觉已春深，一寸光阴一寸金。","黑发不知勤学早，白首方悔读书迟。",
            "咬定青山不放松，立根原在破岩中。","三更灯火五更鸡，正是男儿读书时。",
            "路漫漫其修远兮，吾将上下而求索。","生当作人杰，死亦为鬼雄。",
            "少壮不努力，老大徒伤悲！","富贵必从勤苦得，男儿须读五车书。",
            "三军可夺帅也，匹夫不可夺志也。","千锤万凿出深山，烈火焚烧若等闲。",
            "千磨万击还坚劲，任尔东西南北风。","不经一番寒彻骨，怎得梅花扑鼻香。"};

    public static int getImgSrc(){
        return imgSrc[new Random().nextInt(imgSrc.length)];
    }

    public static String getAddr(){
        return addrs[new Random().nextInt(addrs.length)];
    }

    public static String getPhoneType(){
        return phoneType[new Random().nextInt(phoneType.length)];
    }

    public static String getPublishTime(int i){
        if(i<6){
            return i*10 + new Random().nextInt(10) + "分钟前";
        } else if(i<24){
            return i + "小时前";
        }
        return i + "天前";
    }

    public static String getAuthor(){
        return authors[new Random().nextInt(authors.length)];
    }

    public static String getComContent(){
        return comContent[new Random().nextInt(comContent.length)];
    }


}
