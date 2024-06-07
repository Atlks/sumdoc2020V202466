Atitit mybatis 与sql的问题 prblm v3 uaa

Atitit mybatis 与sql的问题 v2 t88



Atitit mybatis 与sql的问题

If 语句貌似只能在sp里面使用。。在nav里面测试只能在sp使用

@变量可以直接使用。。



idea里面,only   Resources.getResourceAsReader("./mybatis.xml"); user...cant use   /mybatis...cyemyar must yo dyar l ..


	public static void main(String[] args) throws Exception {
		// query();
        String sql = "select 1";
	//	fun_dyn();

       Reader reader = Resources.getResourceAsReader("./mybatis.xml");
     //   Reader reader =new FileReader(new File("D:\\0wkspc\\gameplat-master\\gameplat-core\\src\\test\\resources\\mybatis.xml") );
        //创建会话工厂
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
        SqlSession session = sqlSessionFactory.openSession(true);


