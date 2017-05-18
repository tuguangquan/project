package org.kmd.platform.business.taojinbao.servlet;

import org.kmd.platform.business.taojinbao.service.WeiXinService;
import org.kmd.platform.business.taojinbao.util.SignUtil;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Created by Administrator on 2017/5/18 0018.
 */
public class CoreServlet {
    private static final long serialVersionUID = 4440739483644821986L;
                /**
     	     * 确认请求来自微信服务器
    	     */
                public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
                // 微信加密签名
                String signature = request.getParameter("signature");
               // 时间戳
               String timestamp = request.getParameter("timestamp");
                // 随机数
               String nonce = request.getParameter("nonce");
                // 随机字符串
               String echostr = request.getParameter("echostr");

                PrintWriter out = response.getWriter();
               // 通过检验signature对请求进行校验，若校验成功则原样返回echostr，表示接入成功，否则接入失败
               if (SignUtil.checkSignature(signature, timestamp, nonce)) {
                        out.print(echostr);
                    }
             out.close();
                out = null;
            }

               /**
     	     * 处理微信服务器发来的消息
     	     */
                public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
                    request.setCharacterEncoding("UTF-8");
                    response.setCharacterEncoding("UTF-8");
                    	        // 调用核心业务类接收消息、处理消息
                    String respMessage = WeiXinService.processRequest(request);
                           // 响应消息
                    PrintWriter out = response.getWriter();
                    out.print(respMessage);
                    out.close();

                }

}
