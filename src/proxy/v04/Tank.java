package proxy.v04;

/*
问题：我想记录坦克的移动时间
最简单的方法：修改代码，记录时间
问题2：如果无法改变代码呢，benchmark
使用代理
代理有各种类型
问题：如何实现代理的各种组合？继承？Decorator？
代理的对象改成Movable类型-越来越像的Decorator
V05:动态代理
 */

public class Tank implements Movable{
    @Override
    public void move() {
        System.out.println("tank moving...");
    }

    public static void main(String[] args) {
        new TankTimeProxy(
                new TankLogProxy(
                        new Tank()))
                .move();

    }
}

class TankLogProxy implements Movable{
    Movable m;

    public TankLogProxy(Movable m) {
        this.m = m;
    }

    @Override
    public void move() {
        System.out.println("start move");
        m.move();
        System.out.println("end move");

    }
}

class TankTimeProxy implements Movable{
    Movable m;


    public TankTimeProxy(Movable m) {
        this.m = m;
    }

    @Override
    public void move() {
        long start = System.currentTimeMillis();
        m.move();
        long end = System.currentTimeMillis();

        System.out.println(end - start);

    }
}


interface Movable{

    void move();
}
