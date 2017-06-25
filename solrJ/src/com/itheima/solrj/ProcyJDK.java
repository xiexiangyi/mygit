package com.itheima.solrj;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by xie on 2017/6/23.
 */
public class ProcyJDK {//jdk动态代理增强Diao中的方法
    public static void main(String[] args) {
        //生成接口实现类的代理对象
        Class [] interfaces={Bird.class};
//        Object o = Proxy.newProxyInstance(ProcyJDK.class.getClassLoader(), interfaces, new InvocationHandler() {
//            @Override
//            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
//                return null;
//            }
//        });//匿名内部内的方式
        Bird bird= (Bird) Proxy.newProxyInstance(ProcyJDK.class.getClassLoader(), interfaces, new Ying(new QingTing()));
        bird.fly();
    }
}
class Ying implements InvocationHandler{
    //增强Diao里面的方法，获取Diao的对象

//    private Diao diao;
//    public Ying(Diao diao){
//        this.diao= diao;
//    }
    private Bird bird;
    public Ying(Bird bird){
        this.bird= bird;
    }
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //调用Diao里面的方法
        bird.fly();
        //增强逻辑
        String name = method.getName();
        if("fly".equals(name)){
            //method.invoke(proxy,args);
            System.out.println("增强代码----------");
        }

        return null;
    }
}
interface  Bird{//接口
   public void fly();
 }
// interface  Shake{//接口
//     public void swimming();
// }
//class  yu implements  Shake{//接口实现类
//
//    @Override
//    public void swimming() {
//        System.out.println("yu swimming 100m---------");
//    }
//}
 class  Diao implements  Bird{//接口实现类

     @Override
     public void fly() {
         System.out.println("diao fly 10m---------");
     }
 }
class  QingTing implements  Bird{//接口实现类

    @Override
    public void fly() {
        System.out.println("qingting fly 10000m---------");
    }
}