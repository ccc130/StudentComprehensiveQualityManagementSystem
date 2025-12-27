<template>
  <div class="app-container home">
    <el-row :gutter="20">
      <!-- 左上 - 公告信息 -->
       <el-col :span="12">
        <div class="grid-content bg-purple-light user-info-box">
          <h2>学生综合素质测评管理系统</h2>
          <h1>计科2202</h1>
          <h3>焦阳 22110501052</h3>
          <h3>刘桐 22110501054</h3>
          <h3>陈海旭 22110501073</h3>
        </div>
      </el-col>

      <!-- 右上 - 个人信息 -->
      <el-col :span="12">
        <div class="grid-content bg-purple-light user-info-box">
          <h3>个人信息</h3>
          <div class="user-profile" v-if="state.user">
            <el-avatar 
              size="large" 
              :src="userStore.avatar" 
              style="width: 80px; height: 80px; font-size: 24px; line-height: 80px;"
              v-if="userStore.avatar"
            ></el-avatar>
            <el-avatar 
              size="large"
              style="width: 80px; height: 80px; font-size: 24px; line-height: 80px;"
              v-else
            >
              {{ state.user.userName?.charAt(0) || '用' }}
            </el-avatar>
            <div class="user-details">
              <p>姓名：{{ state.user.userName || '未知' }}</p>
              <p>教学班：{{ state.deptName || '未知' }}</p>
              <p>角色：{{ state.roleGroup || '未知' }}</p>
            </div>
          </div>
          <p v-else>正在加载个人信息...</p>
        </div>
      </el-col>

      <!-- 右下 - 日历 -->
      <el-col :span="12">
        <div class="grid-content bg-purple-light calendar-box">
          <h3>日历</h3>
          <el-calendar v-model="calendarDate"></el-calendar>
        </div>
      </el-col>

      <el-col :span="12">
        <div class="grid-content bg-purple announcement-box">
          <h3>公告信息</h3>
          <ul v-if="notices.length > 0">
            <li v-for="notice in notices" :key="notice.noticeId">
              {{ notice.noticeTitle }}
            </li>
          </ul>
          <p v-else>暂无公告信息</p>
        </div>
        <div class="card" style="flex: 1;height: 400px;" id="clock"></div>
      </el-col>
      
    </el-row>
  </div>
</template>

<script setup name="Index">
import { ref, onMounted } from 'vue';
import { listNotice } from '@/api/system/notice';
import { getUserProfile } from '@/api/system/user';
import useUserStore from '@/store/modules/user'
import { listis, listTeacher, getTeacher, addTeacher, updateTeacher } from '@/api/evaluation/teacher2'
import { ElNotification } from 'element-plus';
import * as echarts from "echarts";

const userStore = useUserStore()
const calendarDate = ref(new Date());
const notices = ref([]);
const teacherEvaluations = ref([]);
const userProfile = ref({});

const state = reactive({
  user: {},
  roleGroup: {},
  postGroup: {},
  deptName: {},
})

function getUser() {
  getUserProfile().then(response => {
    state.user = response.data
    state.roleGroup = response.roleGroup
    state.postGroup = response.postGroup
    state.deptName = response.data.dept.deptName
  })
}
getUser();

// 获取公告信息
const fetchNotices = async () => {
  try {
    const response = await listNotice({ pageNum: 1, pageSize: 5 });
    notices.value = response.rows || [];
  } catch (error) {
    console.error('获取公告信息失败:', error);
  }
};

//时钟
const option = {
  title: {
    text: '实时时钟',
    left: 'center', // 标题水平居中
    top: '2%',     // 距离顶部 10%

    textStyle: {
      fontSize: 24,
      color: '#333',
    },

  },
  series: [
    {
      name: 'hour',
      type: 'gauge',
      startAngle: 90,
      endAngle: -270,
      min: 0,
      max: 12,
      splitNumber: 12,
      clockwise: true,
      axisLine: {
        lineStyle: {
          width: 4,//外边线宽
          color: [[1, 'rgba(0,0,0,0.7)']],
          shadowColor: 'rgba(0, 0, 0, 0.5)',
          shadowBlur: 15
        }
      },
      splitLine: {
        lineStyle: {
          shadowColor: 'rgba(0, 0, 0, 0.3)',
          shadowBlur: 3,
          shadowOffsetX: 1,
          shadowOffsetY: 2
        }
      },
      axisLabel: {
        fontSize: 30, // 钟点标标签字体大小
        distance: 15,// 钟点标签与表盘之间的距离
        formatter: function (value) {
          if (value === 0) {
            return '';
          }
          return value + '';
        }
      },
      pointer: {
        icon: 'path://M2.9,0.7L2.9,0.7c1.4,0,2.6,1.2,2.6,2.6v115c0,1.4-1.2,2.6-2.6,2.6l0,0c-1.4,0-2.6-1.2-2.6-2.6V3.3C0.3,1.9,1.4,0.7,2.9,0.7z',
        width: 6,//时钟针宽度
        length: '55%',
        offsetCenter: [0, '8%'],
        itemStyle: {
          color: '#C0911F',
          shadowColor: 'rgba(0, 0, 0, 0.3)',
          shadowBlur: 8,
          shadowOffsetX: 2,
          shadowOffsetY: 4
        }
      },
      detail: {
        show: false
      },
      title: {
        offsetCenter: [0, '30%']
      },
      data: [
        {
          value: 0
        }
      ]
    },
    {
      name: 'minute',
      type: 'gauge',
      startAngle: 90,
      endAngle: -270,
      min: 0,
      max: 60,
      clockwise: true,
      axisLine: {
        show: false
      },
      splitLine: {
        show: false
      },
      axisTick: {
        show: false
      },
      axisLabel: {
        show: false
      },
      pointer: {
        icon: 'path://M2.9,0.7L2.9,0.7c1.4,0,2.6,1.2,2.6,2.6v115c0,1.4-1.2,2.6-2.6,2.6l0,0c-1.4,0-2.6-1.2-2.6-2.6V3.3C0.3,1.9,1.4,0.7,2.9,0.7z',
        width: 4,//分针宽度
        length: '70%',
        offsetCenter: [0, '8%'],
        itemStyle: {
          color: '#C0911F',
          shadowColor: 'rgba(0, 0, 0, 0.3)',
          shadowBlur: 8,
          shadowOffsetX: 2,
          shadowOffsetY: 4
        }
      },
      anchor: {
        show: true,
        size: 20,
        showAbove: false,
        itemStyle: {
          borderWidth: 15,
          borderColor: '#C0911F',
          shadowColor: 'rgba(0, 0, 0, 0.3)',
          shadowBlur: 8,
          shadowOffsetX: 2,
          shadowOffsetY: 4
        }
      },
      detail: {
        show: false
      },
      title: {
        offsetCenter: ['0%', '-40%']
      },
      data: [
        {
          value: 0
        }
      ]
    },
    {
      name: 'second',
      type: 'gauge',
      startAngle: 90,
      endAngle: -270,
      min: 0,
      max: 60,
      animationEasingUpdate: 'bounceOut',
      clockwise: true,
      axisLine: {
        show: false
      },
      splitLine: {
        show: false
      },
      axisTick: {
        show: false
      },
      axisLabel: {
        show: false
      },
      pointer: {
        icon: 'path://M2.9,0.7L2.9,0.7c1.4,0,2.6,1.2,2.6,2.6v115c0,1.4-1.2,2.6-2.6,2.6l0,0c-1.4,0-2.6-1.2-2.6-2.6V3.3C0.3,1.9,1.4,0.7,2.9,0.7z',
        width: 2,//秒针宽度
        length: '85%',
        offsetCenter: [0, '8%'],
        itemStyle: {
          color: '#C0911F',
          shadowColor: 'rgba(0, 0, 0, 0.3)',
          shadowBlur: 8,
          shadowOffsetX: 2,
          shadowOffsetY: 4
        }
      },
      anchor: {
        show: true,
        size: 10,//表针中心圆盘的大小
        showAbove: true,
        itemStyle: {
          color: '#C0911F',
          shadowColor: 'rgba(0, 0, 0, 0.3)',
          shadowBlur: 8,
          shadowOffsetX: 2,
          shadowOffsetY: 4
        }
      },
      detail: {
        show: false
      },
      title: {
        offsetCenter: ['0%', '-40%']
      },
      data: [
        {
          value: 0
        }
      ]
    }
  ]
};

// 获取教师评价信息并检查未查看记录
const fetchTeacherEvaluations = async () => {
  try {
    const response = await listis({ isevaluation: 0 });
    teacherEvaluations.value = response || [];

    if (teacherEvaluations.value.length > 0) {
      ElNotification({
      title: '提示',
      message: `发现 ${teacherEvaluations.value.length} 条未查看的教师评价`,
      type: 'warning',
      duration: 3000
    });
    }
  } catch (error) {
    console.error('获取教师评价信息失败:', error);
  }
};

// 获取用户个人信息
const fetchUserProfile = async () => {
  try {
    const data = await getUserProfile();
    userProfile.value = data;
  } catch (error) {
    console.error('获取用户个人信息失败:', error);
  }
};

onMounted(() => {
  fetchNotices();
  fetchUserProfile();
  fetchTeacherEvaluations();
  const myChart = echarts.init(document.getElementById('clock'));
  myChart.setOption(option);
  setInterval(function() {
    var date = new Date();
    var second = date.getSeconds();
    var minute = date.getMinutes() + second / 60;
    var hour = (date.getHours() % 12) + minute / 60;
    option.animationDurationUpdate = 300;
    myChart.setOption({
      series: [
        {
          name: 'hour',
          animation: hour !== 0,
          data: [{ value: hour }]
        },
        {
          name: 'minute',
          animation: minute !== 0,
          data: [{ value: minute }]
        },
        {
          animation: second !== 0,
          name: 'second',
          data: [{ value: second }]
        }
      ]
    })
  }, 1000);

});
</script>

<style scoped lang="scss">
.home {
  blockquote {
    padding: 10px 20px;
    margin: 0 0 20px;
    font-size: 17.5px;
    border-left: 5px solid #eee;
  }

  hr {
    margin-top: 20px;
    margin-bottom: 20px;
    border: 0;
    border-top: 1px solid #eee;
  }

  .col-item {
    margin-bottom: 20px;
  }

  ul {
    padding: 0;
    margin: 0;
  }

  font-family: "open sans", "Helvetica Neue", Helvetica, Arial, sans-serif;
  font-size: 13px;
  color: #676a6c;
  overflow-x: hidden;

  ul {
    list-style-type: none;
  }

  h4 {
    margin-top: 0px;
  }

  h2 {
    margin-top: 10px;
    font-size: 26px;
    font-weight: 100;
  }

  p {
    margin-top: 10px;

    b {
      font-weight: 700;
    }
  }

  .update-log {
    ol {
      display: block;
      list-style-type: decimal;
      margin-block-start: 1em;
      margin-block-end: 1em;
      margin-inline-start: 0;
      margin-inline-end: 0;
      padding-inline-start: 40px;
    }
  }
}

.grid-content {
  background: #f5f7fa;
  padding: 20px;
  border-radius: 4px;
  min-height: 200px;
}

.announcement-box {
  min-height: 150px;
}

.user-info-box {
  min-height: 150px;
}

.chart-box {
  min-height: 350px;
}

.calendar-box {
  min-height: 350px;
}

.user-profile {
  display: flex;
  align-items: center;

  .user-details {
    margin-left: 15px;
  }
}
</style>