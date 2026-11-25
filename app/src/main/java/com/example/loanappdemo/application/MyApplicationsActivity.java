
package com.example.loanappdemo.application;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.loanappdemo.R;
import com.example.loanappdemo.dao.ApplicationDao;
import com.example.loanappdemo.model.LoanApplication;

import java.util.List;
import java.util.Locale;

public class MyApplicationsActivity extends AppCompatActivity {

    private LinearLayout applicationContainer;
    private TextView tvEmpty;
    private ApplicationDao applicationDao;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_applications);

        // 绑定XML布局中的控件
        applicationContainer =
                findViewById(R.id.applicationContainer);

        tvEmpty = findViewById(R.id.tvEmpty);

        // 初始化数据访问对象
        applicationDao = new ApplicationDao(this);

        // 获取上一页面传递的用户ID
        userId = getIntent().getStringExtra("userId");
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadApplications();
    }

    // 从数据库查询借款申请
    private void loadApplications() {

        // 清除原有卡片，防止重复显示
        applicationContainer.removeAllViews();

        // 检查用户ID
        if (userId == null || userId.isEmpty()) {
            tvEmpty.setText("尚未获取当前用户信息");
            tvEmpty.setVisibility(View.VISIBLE);
            return;
        }

        try {
            // 调用DAO层查询接口
            List<LoanApplication> applications =
                    applicationDao.getApplicationsByUserId(userId);

            // 判断是否存在申请记录
            if (applications == null || applications.isEmpty()) {
                tvEmpty.setText("暂无借款申请");
                tvEmpty.setVisibility(View.VISIBLE);
                return;
            }

            tvEmpty.setVisibility(View.GONE);

            // 遍历申请记录
            for (LoanApplication application : applications) {
                addApplicationCard(application);
            }

        } catch (Exception e) {
            applicationContainer.removeAllViews();

            tvEmpty.setText("申请记录加载失败");
            tvEmpty.setVisibility(View.VISIBLE);

            Toast.makeText(this,
                    "数据库查询失败",
                    Toast.LENGTH_SHORT).show();

            android.util.Log.e("ApplicationList",
                    "查询申请失败", e);
        }
    }

    // 动态生成借款申请卡片
    private void addApplicationCard(LoanApplication application) {

        LinearLayout card = new LinearLayout(this);
        card.setOrientation(LinearLayout.VERTICAL);
        card.setPadding(dp(20), dp(20), dp(20), dp(20));
        card.setBackgroundColor(Color.WHITE);

        LinearLayout.LayoutParams cardParams =
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );

        cardParams.bottomMargin = dp(16);
        card.setLayoutParams(cardParams);

        // 卡片标题
        TextView title = createText(
                "借款申请",
                18,
                "#222222"
        );
        title.setTypeface(null, android.graphics.Typeface.BOLD);
        card.addView(title);

        // 申请金额
        TextView amount = createText(
                String.format(Locale.CHINA,
                        "¥%,.2f", application.getApplyAmount()),
                30,
                "#222222"
        );
        amount.setTypeface(null, android.graphics.Typeface.BOLD);
        card.addView(amount);

        // 借款期限
        card.addView(createText(
                "借款期限：" + application.getApplyTerm() + "个月",
                15,
                "#666666"
        ));

        // 申请时间
        card.addView(createText(
                "申请时间：" + application.getApplyTime(),
                15,
                "#666666"
        ));

        // 审批状态
        String status = application.getStatus();

        TextView statusView = createText(
                "审批状态：" + status,
                15,
                "#666666"
        );

        if ("已通过".equals(status)) {
            statusView.setTextColor(Color.parseColor("#16803C"));
        } else if ("已拒绝".equals(status)) {
            statusView.setTextColor(Color.parseColor("#D32F2F"));
        } else if ("待审核".equals(status)) {
            statusView.setTextColor(Color.parseColor("#D98E04"));
        } else if ("退回补充".equals(status)) {
            statusView.setTextColor(Color.parseColor("#E67E22"));
        }

        card.addView(statusView);

        // 查看详情按钮
        Button detailButton = new Button(this);
        detailButton.setText("查看申请详情");

        LinearLayout.LayoutParams buttonParams =
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );

        buttonParams.topMargin = dp(16);
        detailButton.setLayoutParams(buttonParams);

        // 暂时验证能否获取正确的申请编号
        detailButton.setOnClickListener(v ->
                Toast.makeText(
                        this,
                        "申请编号：" + application.getApplyId(),
                        Toast.LENGTH_SHORT
                ).show()
        );

        card.addView(detailButton);
        applicationContainer.addView(card);
    }

    // 封装文本控件创建方法
    private TextView createText(String content,
                                int textSize,
                                String color) {

        TextView textView = new TextView(this);

        textView.setText(content);
        textView.setTextSize(textSize);
        textView.setTextColor(Color.parseColor(color));

        LinearLayout.LayoutParams params =
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );

        params.topMargin = dp(10);
        textView.setLayoutParams(params);

        return textView;
    }

    // 将dp转换为像素
    private int dp(int value) {
        return Math.round(value *
                getResources().getDisplayMetrics().density);
    }
}
