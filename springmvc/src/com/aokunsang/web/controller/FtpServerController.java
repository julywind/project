/**
 * 
 */
package com.aokunsang.web.controller;

import com.aokunsang.JsonResultBean;
import com.aokunsang.authority.AuthorityType;
import com.aokunsang.authority.FireAuthority;
import com.aokunsang.ftp.listener.FtpServerListener;
import com.aokunsang.service.AlarmService;
import com.aokunsang.service.FtpUserService;
import com.aokunsang.util.ResultTypeEnum;
import com.aokunsang.util.TextUtil;
import com.aokunsang.web.BaseController;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.ftpserver.FtpServer;
import org.apache.ftpserver.ftplet.Authority;
import org.apache.ftpserver.ftplet.FtpException;
import org.apache.ftpserver.ftplet.User;
import org.apache.ftpserver.ftplet.UserManager;
import org.apache.ftpserver.impl.DefaultFtpServer;
import org.apache.ftpserver.usermanager.UserFactory;
import org.apache.ftpserver.usermanager.impl.WritePermission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletContext;
import java.util.ArrayList;
import java.util.List;

/**
 * 登陆方法
 * @author tushen
 * @date Nov 4, 2011
 */
@Controller
public class FtpServerController extends BaseController{

	@Autowired
	private FtpUserService ftpUserService;

	@RequestMapping(value="/ftp/start")
    /**
     * 开启FTPServer
     * @param servletContext 可以从action中获得也可以ServletActionContext.getServletContext();
     */
    public boolean startFtpServer(ServletContext servletContext) {
        System.out.println("Starting FtpServer");

        boolean flg = true;
        try {
            FtpServer server = (FtpServer) servletContext.getAttribute(FtpServerListener.FTPSERVER_CONTEXT_NAME);

            if(server != null) {
                if(server.isSuspended())
                    server.resume();//恢复

            }else{

                WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(servletContext);

                server = (FtpServer) ctx.getBean("ftpServer");

                servletContext.setAttribute(FtpServerListener.FTPSERVER_CONTEXT_NAME, server);
                server.start();
            }
            System.out.println("FtpServer started");
        } catch (Exception e) {
            flg =  false;
            throw new RuntimeException("Failed to start FtpServer", e);
        }
        return flg;
    }

    //由于apache FTPserver不提供重启功能如果需要手动关闭serve可以将其进行挂起，以方便恢复
    /**
     * 关闭FTPServer
     * @param servletContext
     * @return
     */
    @RequestMapping(value="/ftp/stop")
    public boolean stopFtpServer(ServletContext servletContext) {
        System.out.println("Stopping FtpServer");

        FtpServer server = (FtpServer) servletContext.getAttribute(FtpServerListener.FTPSERVER_CONTEXT_NAME);

        if(server != null) {
            if(!server.isStopped()&&!server.isSuspended())
                server.suspend();

            //servletContext.removeAttribute(FtpServerListener.FTPSERVER_CONTEXT_NAME);

            System.out.println("FtpServer suspend");
        } else {
            System.out.println("No running FtpServer found");
        }
        return true;
    }

    //对用户进行操作
    /**
     * 申请FTP帐号
     * @param userName
     * @param sce
     * @return
     * @throws FtpException
     */
    @RequestMapping(value="/ftp/addUser")
    public String applyAccount(String userName, ServletContext sce) throws FtpException {
        String psw="";
        //PropertiesUserManagerFactory userManagerFactory = new PropertiesUserManagerFactory();
        //File f = new File(AgentFileService.class.getClassLoader().getResource("users.properties").getFile());
        //userManagerFactory.setFile(f);
        //DbUserManagerFactory userManagerFactory = new DbUserManagerFactory();
        //明文密码保存于配置文件中 发面时改为MD5加密 同时修改配置文件applictionContext-ftp
        //userManagerFactory.setPasswordEncryptor(new ClearTextPasswordEncryptor());
        //UserManager um = userManagerFactory.createUserManager();
        DefaultFtpServer server = (DefaultFtpServer) sce.getAttribute(FtpServerListener.FTPSERVER_CONTEXT_NAME);
        if(server!=null){
            UserManager um = server.getUserManager();
            if(um.doesExist(userName)){
                um.delete(userName);
            }
            psw = RandomStringUtils.randomAlphanumeric(8);
            UserFactory userFact = new UserFactory();
            userFact.setName(userName);
            userFact.setPassword(psw);
            userFact.setHomeDirectory("/"+userName);
            userFact.setMaxIdleTime(600000);//10分钟无操作自动断开连接
            List<Authority> alist = new ArrayList<Authority>();
            Authority a = new WritePermission();//写权限
            alist.add(a);
            userFact.setAuthorities(alist);
            User user = userFact.createUser();
            um.save(user);
        }
        return psw;
    }

    /**
     * 删除FTP帐号
     * @param userName
     * @param sce
     * @return
     * @throws FtpException
     */
    @RequestMapping(value="/ftp/delUser")
    public void removeAccount(String userName, ServletContext sce) throws FtpException {
        //PropertiesUserManagerFactory userManagerFactory = new PropertiesUserManagerFactory();
        //File f = new File(AgentFileService.class.getClassLoader().getResource("users.properties").getFile());
        //userManagerFactory.setFile(f);
        //DbUserManagerFactory userManagerFactory = new DbUserManagerFactory();
        //明文密码保存于配置文件中 发面时改为MD5加密 同时修改配置文件applictionContext-ftp
        //userManagerFactory.setPasswordEncryptor(new ClearTextPasswordEncryptor());
        //UserManager um = userManagerFactory.createUserManager();

        DefaultFtpServer server = (DefaultFtpServer) sce.getAttribute(FtpServerListener.FTPSERVER_CONTEXT_NAME);
        if (server != null) {
            UserManager um = server.getUserManager();
            String[] users = um.getAllUserNames();
            if (users != null && users.length > 0) {
                //for (String userName : users) {
                    if (!userName.equals("admin")
                            && um.doesExist(userName)) {
                        um.delete(userName);
                    }
                //}
            }
        }
    }

    @FireAuthority(authorityTypes = {AuthorityType.USER_NORMAL}, resultType= ResultTypeEnum.page)
    @RequestMapping(value="/ftp/list")
    public ModelAndView getUsers(Integer offset,Integer limit,String whereCondition){
        String sql = "select %s from FTP_USER "+ (TextUtil.isEmpty(whereCondition)?"":(" where "+whereCondition));
        String sql2 = sql;
        if(limit!=null&&limit>=0)
        {
            sql2 += " limit "+limit;
        }
        if(offset!=null&&offset>=0)
        {
            sql2 += " offset "+offset;
        }
        return new ModelAndView("ftp/list", "result", new JsonResultBean(true,
                ftpUserService.getCount(String.format(sql,"count(*) as totalCount")),
                ftpUserService.query(String.format(sql2,"*"), null)));
    }
}
