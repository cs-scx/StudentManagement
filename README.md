# 学生信息管理系统
### 测试环境：openJDK-11.0.9,  mysql-8.0.23
### clone项目到本地，执行sql.sql中的SQL（进入mysql，使用source ./sql.sql(替换成你的sql文件在本地的绝对路径)）即可创建数据库，创建数据表，并添加数据

* 登录和注册页面

  > login.html(获取用户，用户密码)
  >
  > register.html(获取新的用户名，密码，权限等级)

* 处理登录请求

  > 验证用户输入的登录信息
  >
  > 信息核对正确后，根据用户权限等级跳转到不同的页面
  >
  > > 管理员页面（admin.jsp) 普通用户页面（user.jsp)，保存用户登录信息
  >
  > 登录失败，设置失败信息，传达至message.jsp页面
  >
  > Message.jsp页面显示重新登录链接

* > 为管理员(admin.jsp)和普通用户(usr.jsp)提供不同的操作接口

  **admin.jsp中能够对所有的数据进行增删改查，user.jsp中只能查看数据**

  

### LoginServlet

````java
protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String level = null;
        UserDao userDao = new UserDao();
        User user = userDao.login(username, password);
        if(user != null){
            level = user.getLevel();
            if(level.equals("用户")){
                request.getSession().setAttribute("user", user);
                request.getRequestDispatcher("user.jsp").forward(request, response);
            }
            else{
                request.getSession().setAttribute("admin", user);
                request.getRequestDispatcher("admin.jsp").forward(request, response);
            }
        }else {
            request.setAttribute("info"," 错误:用户名或密码错误！");
            request.getRequestDispatcher("message.jsp").forward(request, response);
        }
    }

````

### 类User，UserDao

### userDao中的方法

````java
    public boolean userIsExist(String username) {}

    public User login(String username, String password) {}

    public User register(String username, String password, String level) {}

    public String level(String username){}

    public ArrayList<User> query_all_user() {}

    public int insert_user(String username, String password, String level) {}

    public int delete_user(String username) {}

    public int alter_user(String username, String after_username, String after_password, String after_level) {}

````





### RegisterServlet

```java
@WebServlet(name = "RegisterServlet", urlPatterns = "/RegisterServlet")
public class RegisterServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String level = request.getParameter("level");
        UserDao userDao = new UserDao();
        User user = userDao.register(username, password,level);
        if(user != null){
            if (level.equals("用户")) {
                request.getSession().setAttribute("user", user);
                request.getRequestDispatcher("user.jsp").forward(request, response);
            }else{
                request.getSession().setAttribute("admin", user);
                request.getRequestDispatcher("admin.jsp").forward(request, response);
            }
        }else {
            request.setAttribute("info"," 错误:已存在该用户,不能重复注册！");
            request.getRequestDispatcher("message.jsp").forward(request, response);
        }
    }

```

**用户名存在则注册失败 ，注册成功直接跳转到admin.jsp或user.jsp页面**



### admin.js和usr.js发送AJAX请求

```javascript
function query_all(object) {
    var xmlhttp;
    if (window.XMLHttpRequest) {
        //  IE7+, Firefox, Chrome, Opera, Safari 浏览器执行代码
        xmlhttp = new XMLHttpRequest();
    }
    else {
        // IE6, IE5 浏览器执行代码
        xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
    }
    xmlhttp.onreadystatechange = function () {
        if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {

            document.getElementById("result").innerHTML = xmlhttp.responseText;
        }
    }
    xmlhttp.open("GET", "/StudentManagement/AdminDao?action=query_all_" + object, true);
    xmlhttp.send();
}
```

### adminDao处理AJAX请求，以查询所有用户为例

```java
	//查询所有用户
	protected void query_all_user(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");
		UserDao userDao = new UserDao();
		
		ArrayList<User> results = userDao.query_all_user();
		PrintWriter out = response.getWriter();
		//输出结果
		if(results != null){
			out.write("<div class='all'>");
			out.write("<div><span>用户名</span><span>密码</span><span>权限级别</span></div>");
			for(User i: results){
				out.write("<div>");
				out.write("<span>"+i.getUsername()+"</span>");
				out.write("<span>"+i.getPassword()+"</span>");
				out.write("<span>"+i.getLevel()+"</span>");
				out.write("</div>");
			}
			out.write("</div>");
		}
		
		out.flush();
		out.close();
	}
	//插入用户

```

