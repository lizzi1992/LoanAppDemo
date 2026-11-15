package com.example.loanappdemo; // 注意改成你自己的包名

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.loanappdemo.dao.ApplicationDao;
import com.example.loanappdemo.dao.UserDao;
import com.example.loanappdemo.model.LoanApplication;
import com.example.loanappdemo.model.User;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d("DB_TEST", "===== 开始测试 DAO 数据层 =====");

        UserDao userDao = new UserDao(this);
        ApplicationDao appDao = new ApplicationDao(this);

        // 1. 模拟王悦涵注册用户
        String testUserId = UUID.randomUUID().toString().replace("-", "");
        User newUser = new User();
        newUser.setUserId(testUserId);
        newUser.setUserName("张三");
        newUser.setPhone("13800000000");
        newUser.setIdCard("510100200001011234");
        newUser.setStatus(0);

        boolean isUserAdded = userDao.addUser(newUser);
        Log.d("DB_TEST", "1. 插入用户成功？ " + isUserAdded);

        // 2. 模拟登录：根据手机号查用户
        User loginUser = userDao.getUserByPhone("13800000000");
        if (loginUser != null) {
            Log.d("DB_TEST", "2. 查到了用户：" + loginUser.getUserName() + "，ID是：" + loginUser.getUserId());
        }

        // 3. 模拟高同学提交借款申请
        String testApplyId = UUID.randomUUID().toString().replace("-", "");
        LoanApplication newApp = new LoanApplication();
        newApp.setApplyId(testApplyId);
        newApp.setUserId(testUserId);
        newApp.setProductId("P001");
        newApp.setApplyAmount(5000.00);
        newApp.setApplyTerm(12);
        newApp.setApplyTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date()));

        boolean isAppAdded = appDao.addApplication(newApp);
        Log.d("DB_TEST", "3. 提交借款申请成功？ " + isAppAdded);

        // 4. 你的核心工作：模拟后台审批通过
        boolean isStatusUpdated = appDao.updateApplicationStatus(testApplyId, "已通过");
        Log.d("DB_TEST", "4. 模拟审批通过成功？ " + isStatusUpdated);

        // 5. 模拟王梓萌查询申请列表
        List<LoanApplication> myApps = appDao.getApplicationsByUserId(testUserId);
        if (myApps != null && myApps.size() > 0) {
            Log.d("DB_TEST", "5. 查询到该用户的申请数量：" + myApps.size());
            Log.d("DB_TEST", "   第一笔申请状态是：" + myApps.get(0).getStatus());
        }
    }
}