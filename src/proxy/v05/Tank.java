package proxy.v05;

/*
问题：我想记录坦克的移动时间
最简单的方法：修改代码，记录时间
问题2：如果无法改变代码呢，benchmark
使用代理
代理有各种类型
问题：如何实现代理的各种组合？继承？Decorator？
代理的对象改成Movable类型-越来越像的Decorator
V05:如果有stop方法需要代理
如果想让logproxy可以重用，不久可以代理tank，还可以代理任何其他可以代理的类型object
此时：分离代理行为和被代理对象
使用jdk的动态代理
 */

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Random;

public class Tank implements Movable{
    @Override
    public void move() {
        System.out.println("tank moving...");
        try{
            Thread.sleep(new Random().nextInt(10000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Tank tank = new Tank();
        Movable m = (Movable) Proxy.newProxyInstance(Tank.class.getClassLoader(),
                new Class[]{Movable.class},
                new Loghandler(tank)
        );
        m.move();
    }
}

class Loghandler implements InvocationHandler{
    Tank tank;

    public Loghandler(Tank tank) {
        this.tank = tank;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("method "+ method.getName() + " start...");
        Object o = method.invoke(tank, args);
        System.out.println("method "+ method.getName() + " end!" );
        return o;
    }
}

interface Movable{

    void move();
}
