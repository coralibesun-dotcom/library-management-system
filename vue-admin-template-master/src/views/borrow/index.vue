<template>
  <div class="app-container">
    <!-- 借阅记录表格：纯展示 + 还书按钮，没有新增/编辑/搜索/分页 -->
    <el-table
      v-loading="listLoading"
      :data="list"
      element-loading-text="加载中..."
      border
      fit
      highlight-current-row
    >
      <el-table-column label="ID" prop="id" width="80" align="center" />

      <el-table-column label="用户ID" prop="userId" width="100" align="center" />

      <el-table-column label="图书ID" prop="bookId" width="100" align="center" />

      <el-table-column label="借阅时间" prop="borrowTime" min-width="180" align="center" />

      <el-table-column label="归还时间" prop="returnTime" min-width="180" align="center">
        <template slot-scope="scope">
          {{ scope.row.returnTime || '—' }}
        </template>
      </el-table-column>

      <!-- 状态列：用 el-tag 显示彩色标签（新考点） -->
      <el-table-column label="状态" prop="status" width="100" align="center">
        <template slot-scope="scope">
          <el-tag :type="statusTagType(scope.row.status)">
            {{ statusText(scope.row.status) }}
          </el-tag>
        </template>
      </el-table-column>

      <!-- 操作列：只有"借阅中"的记录才显示还书按钮 -->
      <el-table-column label="操作" width="120" align="center" fixed="right">
        <template slot-scope="scope">
          <el-button
            v-if="scope.row.status === 0"
            type="warning"
            size="mini"
            @click="handleReturn(scope.row)"
          >
            还书
          </el-button>
          <span v-else>—</span>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script>
import { getAllBorrows, returnBook } from '@/api/borrow'

export default {
  data() {
    return {
      list: [],
      listLoading: true
    }
  },
  created() {
    this.getList()
  },
  methods: {
    // 1. 拉取列表：调 getAllBorrows()，res.data 直接赋给 list（和分类页一样，不分页）
    async getList() {
      // TODO: 1) 开 loading  2) await getAllBorrows()  3) this.list = res.data  4) 关 loading
      this.listLoading = true
      const res = await getAllBorrows()
      this.list = res.data
      this.listLoading = false
    },

    // 2. 还书：$confirm 三件套（和分类页删除一模一样的套路，只是换接口）
    handleReturn(row) {
      // TODO: $confirm('确定要归还这本图书吗？'...) → .then(async()=>{ await returnBook(row.id) + 提示 + getList }) → .catch(()=>{})
      this.$confirm('确定要归还这本图书吗？', '提示', { type: 'warning' })
        .then(async() => {
          await returnBook(row.id)
          this.$message.success('归还成功')
          this.getList()
        })
        .catch(() => {})
    },

    // ↓↓↓ 以下两个是辅助函数，我写好了不用你补，但你要看懂 ↓↓↓

    // 根据状态码返回标签文字
    statusText(status) {
      const map = { 0: '借阅中', 1: '已归还', 2: '逾期' }
      return map[status] || '未知'
    },

    // 根据状态码返回标签颜色（el-tag 的 type 属性）
    statusTagType(status) {
      const map = { 0: '', 1: 'success', 2: 'danger' }
      return map[status] || 'info'
    }
  }
}
</script>
