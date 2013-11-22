package cc.algs.proxy;

public class Client {

    /**
     * @param args
     */
    public static void main(String[] args) {

        BusinessHandler businessHandler = new BusinessHandler();
        UserManager userManager = (UserManager) businessHandler.newProxyInstance(new UserManagerImpl());

        // userManager.addUser("0001", "张三");
        // userManager.delUser("0001");
        // System.out.println(userManager.getClass().getName());

        String name = userManager.test("0001");
        // String name = ((UserManagerImpl) logHandler.newProxyInstance(new UserManagerImpl())).test("0001");
        System.out.println("Client.main() --- " + name);
    }

}
