<template>
  <div class="dashboard-container" v-loading="loading">
    <div class="welcome">你好，{{ name }}，这里是图书馆概览</div>

    <el-row :gutter="20">
      <el-col v-for="card in cards" :key="card.label" :span="6">
        <el-card class="stat-card" shadow="hover">
          <div class="stat-icon" :style="{ background: card.color }">
            <i :class="card.icon" />
          </div>
          <div class="stat-info">
            <div class="stat-num">{{ card.value }}</div>
            <div class="stat-label">{{ card.label }}</div>
          </div>
        </el-card>
      </el-col>
    </el-row>

    <el-card class="tip-card" shadow="never">
      <div slot="header">说明</div>
      <ul class="tip-list">
        <li><b>当前在借</b>：状态为「借阅中」的记录总数</li>
        <li><b>逾期</b>：借阅超过 30 天未还，由系统每天凌晨 1 点自动标记</li>
        <li><b>今日借出 / 归还</b>：按 borrow_time / return_time 当天统计</li>
        <li>普通用户在图书页可借阅，上限 5 本；管理员可管理图书与上下架</li>
      </ul>
    </el-card>
  </div>
</template>

<script>
import { mapGetters } from 'vuex'
import { getStats } from '@/api/stats'

export default {
  name: 'Dashboard',
  computed: {
    ...mapGetters(['name']),
    // 把后端返回的数字拼成卡片数组，模板里 v-for 渲染
    cards() {
      const s = this.stats || {}
      return [
        { label: '图书种类', value: s.bookCount || 0, icon: 'el-icon-reading', color: '#409EFF' },
        { label: '馆藏库存', value: s.totalStock || 0, icon: 'el-icon-files', color: '#67C23A' },
        { label: '当前在借', value: s.borrowingCount || 0, icon: 'el-icon-shopping-cart-2', color: '#E6A23C' },
        { label: '今日借出', value: s.todayBorrow || 0, icon: 'el-icon-top', color: '#909399' },
        { label: '今日归还', value: s.todayReturn || 0, icon: 'el-icon-bottom', color: '#13C2C2' },
        { label: '逾期', value: s.overdueCount || 0, icon: 'el-icon-warning', color: '#F56C6C' },
        { label: '用户总数', value: s.userCount || 0, icon: 'el-icon-user', color: '#722ED1' },
        { label: '分类总数', value: s.categoryCount || 0, icon: 'el-icon-menu', color: '#EB2F96' }
      ]
    }
  },
  data() {
    return {
      loading: true,
      stats: {}
    }
  },
  created() {
    this.fetchStats()
  },
  methods: {
    async fetchStats() {
      this.loading = true
      try {
        const res = await getStats()
        this.stats = res.data || {}
      } finally {
        this.loading = false
      }
    }
  }
}
</script>

<style lang="scss" scoped>
.dashboard {
  &-container {
    margin: 20px;
  }
}
.welcome {
  font-size: 20px;
  font-weight: 600;
  margin-bottom: 20px;
  color: #303133;
}
.stat-card {
  margin-bottom: 20px;
  ::v-deep .el-card__body {
    display: flex;
    align-items: center;
    padding: 20px;
  }
}
.stat-icon {
  width: 56px;
  height: 56px;
  border-radius: 8px;
  display: flex;
  align-items: center;
  justify-content: center;
  margin-right: 16px;
  i {
    font-size: 28px;
    color: #fff;
  }
}
.stat-info {
  flex: 1;
}
.stat-num {
  font-size: 28px;
  font-weight: 700;
  color: #303133;
  line-height: 1.2;
}
.stat-label {
  font-size: 13px;
  color: #909399;
  margin-top: 4px;
}
.tip-card {
  margin-top: 10px;
  .tip-list {
    margin: 0;
    padding-left: 20px;
    color: #606266;
    line-height: 2;
    font-size: 14px;
  }
}
</style>
