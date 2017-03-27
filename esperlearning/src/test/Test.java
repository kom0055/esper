package test;

import com.espertech.esper.client.*;

/**
 * Created by apple on 2017/3/22.
 */

 class Apple {
    private int id;
    private int price;

    public Apple() {
    }

    public Apple(int id, int price) {
        this.id = id;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public int getPrice() {
        return price;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}

class AppleListenr implements UpdateListener{
    @Override
    public void update(EventBean[] eventBeans, EventBean[] eventBeans1) {
        if (eventBeans!=null){
            Double avg =(Double) eventBeans[0].get("avg(price)");
            System.out.println("Apple's average price is "+avg);
        }
    }
}

public class Test{
    public static void main(String[] args) throws InterruptedException{
        EPServiceProvider epServiceProvider= EPServiceProviderManager.getDefaultProvider();

        EPAdministrator administrator=epServiceProvider.getEPAdministrator();

        String product =Apple.class.getName();
        String epl="select avg(price) from "+product+".win:length_batch(3)";

        EPStatement statement =administrator.createEPL(epl);

        statement.addListener(new AppleListenr());
        EPRuntime runtime =epServiceProvider.getEPRuntime();

        runtime.sendEvent(new Apple(1,5));
        runtime.sendEvent(new Apple(2,2));
        runtime.sendEvent(new Apple(3,5));



    }
}








