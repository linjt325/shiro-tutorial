package top.linjt.test;

public class ExtendTest {

    protected String s = "123";

    public void soutS(){
        System.out.println(this.s);
    }

    class B extends ExtendTest {

        protected String s = "456";

        @Override
        public void soutS(){
            System.out.println(s);
        }
    }


    public static void main(String[] args) {

        ExtendTest extendTest = new ExtendTest().new B();
        extendTest.soutS();

    }


}
