<template>
  <div class="app-container">
    <!-- 顶部：状态筛选 + 搜索按钮（分页页面的标配） -->
    <div style="margin-bottom: 15px">
      <el-select
        v-model="listQuery.status"
        placeholder="全部状态"
        clearable
        style="width: 150px"
        @change="handleSearch"
      >
        <el-option label="借阅中" :value="0" />
        <el-option label="已归还" :value="1" />
        <el-option label="逾期" :value="2" />
      </el-select>
      <el-button type="primary" icon="el-icon-search" @click="handleSearch">搜索</el-button>
    </div>

    <!-- 借阅记录表格 -->
    <el-table
      v-loading="listLoading"
      :data="list"
      element-loading-text="加载中..."
      border
      fit
      highlight-current-row
    >
      <el-table-column label="ID" prop="id" width="80" align="center" />

      <!-- 用户名列只有管理员才有意义（普通用户看到的永远是自己） -->
      <el-table-column v-if="isAdmin" label="用户名" prop="username" min-width="120" align="center" show-overflow-tooltip />

      <!-- 书名列：后端 JOIN 出书名，不用再盯着裸数字 -->
      <el-table-column label="书名" prop="bookTitle" min-width="200" align="center" show-overflow-tooltip />

      <el-table-column label="借阅时间" prop="borrowTime" min-width="180" align="center" />

      <el-table-column label="归还时间" prop="returnTime" min-width="180" align="center">
        <template slot-scope="scope">
          {{ scope.row.returnTime || '—' }}
        </template>
      </el-table-column>

      <!-- 状态列：用 el-tag 显示彩色标签 -->
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

    <!-- 分页器：和图书页同款 -->
    <el-pagination
      style="margin-top: 15px"
      background
      layout="total, prev, pager, next"
      :total="total"
      :page-size="listQuery.pageSize"
      :current-page="listQuery.pageNum"
      @current-change="handlePageChange"
    />
  </div>
</template>

<script>
import { getBorrowPage, returnBook } from '@/api/borrow'
import { mapGetters } from 'vuex'

export default {
  computed: {
    ...mapGetters(['roles']),
    isAdmin() {
      return this.roles.includes('admin')
    }
  },
  data() {
    return {
      list: [],
      total: 0,
      listLoading: true,
      // 分页参数：pageNum 从 1 开始；status 不传 = 查全部
      listQuery: {
        pageNum: 1,
        pageSize: 10,
        status: undefined
      }
    }
  },
  created() {
    this.getList()
  },
  methods: {
    // 1. 分页拉取：管理员和普通用户调同一个接口，后端按角色收敛数据范围
    async getList() {
      this.listLoading = true
      try {
        const res = await getBorrowPage(this.listQuery)
        this.list = res.data.records
        this.total = res.data.total
      } finally {
        this.listLoading = false
      }
    },

    // 2. 筛选搜索：永远回到第 1 页再查（第 3 页筛选出来的结果可能只有 1 页）
    handleSearch() {
      this.listQuery.pageNum = 1
      this.getList()
    },

    // 3. 翻页：改页码重新拉
    handlePageChange(pageNum) {
      this.listQuery.pageNum = pageNum
      this.getList()
    },

    // 4. 还书：$confirm 三件套
    handleReturn(row) {
      this.$confirm('确定要归还这本图书吗？', '提示', { type: 'warning' })
        .then(async() => {
          await returnBook(row.id)
          this.$message.success('归还成功')
          this.getList()
        })
        .catch(() => {})
    },

    // ↓↓↓ 辅助函数 ↓↓↓

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
