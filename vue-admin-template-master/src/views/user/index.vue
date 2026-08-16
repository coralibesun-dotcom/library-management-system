<template>
  <div class="app-container">
    <!-- 搜索栏：用户名 + 角色筛选 + 状态筛选 + 搜索按钮 -->
    <div style="margin-bottom: 15px; display: flex; gap: 10px; align-items: center">
      <el-input
        v-model="listQuery.username"
        placeholder="搜索用户名"
        style="width: 200px"
        clearable
        @keyup.enter.native="handleSearch"
      />

      <el-select
        v-model="listQuery.role"
        placeholder="角色筛选"
        clearable
        style="width: 140px"
        @change="handleSearch"
      >
        <el-option label="管理员" value="ADMIN" />
        <el-option label="普通用户" value="USER" />
      </el-select>

      <el-select
        v-model="listQuery.status"
        placeholder="状态筛选"
        clearable
        style="width: 140px"
        @change="handleSearch"
      >
        <el-option label="启用" :value="1" />
        <el-option label="禁用" :value="0" />
      </el-select>

      <el-button type="primary" icon="el-icon-search" @click="handleSearch">搜索</el-button>
    </div>

    <!-- 表格：6 列 + 操作列 -->
    <el-table
      v-loading="listLoading"
      :data="list"
      element-loading-text="加载中..."
      border
      fit
      highlight-current-row
    >
      <el-table-column label="ID" prop="id" width="80" align="center" />

      <el-table-column label="用户名" prop="username" min-width="150" show-overflow-tooltip />

      <el-table-column label="角色" width="100" align="center">
        <template slot-scope="scope">
          <el-tag :type="roleTagType(scope.row.role)">
            {{ roleText(scope.row.role) }}
          </el-tag>
        </template>
      </el-table-column>

      <el-table-column label="状态" width="100" align="center">
        <template slot-scope="scope">
          <el-tag :type="statusTagType(scope.row.status)">
            {{ statusText(scope.row.status) }}
          </el-tag>
        </template>
      </el-table-column>

      <el-table-column label="创建时间" prop="createTime" min-width="180" align="center" />

      <el-table-column label="操作" width="200" align="center" fixed="right">
        <template slot-scope="scope">
          <el-button
            :type="scope.row.status === 1 ? 'danger' : 'success'"
            size="mini"
            @click="handleToggleStatus(scope.row)"
          >
            {{ scope.row.status === 1 ? '禁用' : '启用' }}
          </el-button>
          <el-button
            type="warning"
            size="mini"
            @click="handleRoleChange(scope.row)"
          >
            {{ scope.row.role === 'USER' ? '设为管理员' : '设为普通用户' }}
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 分页器（和图书页一模一样） -->
    <el-pagination
      style="margin-top: 15px; text-align: right"
      background
      layout="total, prev, pager, next, jumper"
      :total="total"
      :current-page.sync="listQuery.pageNum"
      :page-size="listQuery.pageSize"
      @current-change="handlePageChange"
    />
  </div>
</template>

<script>
import { getAdminUserPage, updateUserStatus, updateUserRole } from '@/api/user'

export default {
  data() {
    return {
      list: [],
      total: 0,
      listLoading: true,
      listQuery: {
        pageNum: 1,
        pageSize: 10,
        username: '',
        role: undefined,
        status: undefined
      }
    }
  },
  created() {
    this.getList()
  },
  methods: {
    // ↓↓↓ 以下 5 个方法由你来填，照着 book/index.vue 的套路改 ↓↓↓

    // 1. 拉取列表：和图书页一模一样！getAdminUserPage(this.listQuery) → res.data.records / res.data.total
    async getList() {
      // TODO: 1) 开 loading  2) await getAdminUserPage(this.listQuery)  3) this.list = res.data.records  4) this.total = res.data.total  5) 关 loading
      this.listLoading = true
      const res = await getAdminUserPage(this.listQuery)
      this.list = res.data.records
      this.total = res.data.total
      this.listLoading = false
    },

    // 2. 搜索：pageNum 重置为 1，再调 getList（和图书页一模一样）
    handleSearch() {
      // TODO
      this.listQuery.pageNum = 1
      this.getList()
    },

    // 3. 翻页：更新 pageNum，再调 getList（和图书页一模一样）
    handlePageChange(page) {
      // TODO
      this.listQuery.pageNum = page
      this.getList()
    },

    // 4. 启用/禁用：和图书页 handleToggleStatus 一模一样的套路！
    //    targetStatus = row.status === 1 ? 0 : 1
    //    actionText = targetStatus === 1 ? '启用' : '禁用'
    //    $confirm → await updateUserStatus({ id: row.id, status: targetStatus }) → 提示 → getList → catch
    handleToggleStatus(row) {
      // TODO
      const targetStatus = row.status === 1 ? 0 : 1
      const actionText = targetStatus === 1 ? '启用' : '禁用'
      this.$confirm('确定要' + actionText + '该用户吗？', '提示', { type: 'warning' })
        .then(async() => {
          await updateUserStatus({ id: row.id, status: targetStatus })
          this.$message.success(actionText + '成功')
          this.getList()
        })
        .catch(() => {})
    },

    // 5. 改角色：和上面 handleToggleStatus 套路一样，但换成了角色！
    //    targetRole = row.role === 'USER' ? 'ADMIN' : 'USER'
    //    actionText = targetRole === 'ADMIN' ? '设为管理员' : '设为普通用户'
    //    $confirm → await updateUserRole({ id: row.id, role: targetRole }) → 提示 → getList → catch
    handleRoleChange(row) {
      // TODO
      const targetRole = row.role === 'USER' ? 'ADMIN' : 'USER'
      const actionText = targetRole === 'ADMIN' ? '设为管理员' : '设为普通用户'
      this.$confirm('确定要' + actionText + '吗？', '提示', { type: 'warning' })
        .then(async() => {
          await updateUserRole({ id: row.id, role: targetRole })
          this.$message.success(actionText + '成功')
          this.getList()
        })
        .catch(() => {})
    },

    // ↓↓↓ 以下 4 个是辅助函数，我写好了不用你补，但你要看懂 ↓↓↓

    statusText(status) {
      const map = { 1: '启用', 0: '禁用' }
      return map[status] || '未知'
    },

    statusTagType(status) {
      const map = { 1: 'success', 0: 'danger' }
      return map[status] || 'info'
    },

    roleText(role) {
      const map = { ADMIN: '管理员', USER: '普通用户' }
      return map[role] || '未知'
    },

    roleTagType(role) {
      const map = { ADMIN: 'danger', USER: '' }
      return map[role] || 'info'
    }
  }
}
</script>
