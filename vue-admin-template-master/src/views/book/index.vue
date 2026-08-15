<template>
  <div class="app-container">
    <!-- 改动①：搜索栏搬到表格外面、上面 -->
    <div style="margin-bottom: 15px">
      <el-input
        v-model="listQuery.keyword"
        placeholder="书名 / 作者"
        style="width: 220px"
        clearable
        @keyup.enter.native="handleSearch"
      />
      <el-select
        v-model="listQuery.categoryId"
        placeholder="全部分类"
        clearable
        style="width: 150px; margin-right: 10px"
        @change="handleSearch"
      >
        <el-option
          v-for="item in categoryList"
          :key="item.id"
          :label="item.name"
          :value="item.id"
        />
      </el-select>

      <el-button type="primary" icon="el-icon-search" @click="handleSearch">搜索</el-button>
      <el-button type="success" icon="el-icon-plus" @click="handleAdd">新增图书</el-button>
    </div>

    <el-table
      v-loading="listLoading"
      :data="list"
      element-loading-text="加载中..."
      border
      fit
      highlight-current-row
    >
      <el-table-column label="ID" width="80" align="center">
        <template slot-scope="scope">
          {{ scope.row.id }}
        </template>
      </el-table-column>

      <el-table-column label="书名" min-width="220" show-overflow-tooltip>
        <template slot-scope="scope">
          {{ scope.row.title }}
        </template>
      </el-table-column>

      <el-table-column label="作者" min-width="140" align="center" show-overflow-tooltip>
        <template slot-scope="scope">
          {{ scope.row.author }}
        </template>
      </el-table-column>

      <el-table-column label="出版社" min-width="180" align="center" show-overflow-tooltip>
        <template slot-scope="scope">
          {{ scope.row.publisher }}
        </template>
      </el-table-column>

      <el-table-column label="库存" width="90" align="center">
        <template slot-scope="scope">
          {{ scope.row.stock }}
        </template>
      </el-table-column>

      <el-table-column label="状态" width="100" align="center">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.status === 1" type="success">上架</el-tag>
          <el-tag v-else-if="scope.row.status === 2" type="danger">下架</el-tag>
          <el-tag v-else type="info">草稿</el-tag>
        </template>
      </el-table-column>

      <el-table-column label="入库时间" min-width="160" align="center">
        <template slot-scope="scope">
          <i class="el-icon-time" />
          <span>{{ scope.row.createTime }}</span>
        </template>
      </el-table-column>

      <el-table-column label="操作" width="160" align="center" fixed="right">
        <template slot-scope="scope">
          <el-button type="primary" size="mini" @click="handleEdit(scope.row)">编辑</el-button>
          <el-button
            size="mini"
            :type="scope.row.status === 1 ? 'danger' : 'success'"
            @click="handleToggleStatus(scope.row)"
          >
            {{ scope.row.status === 1 ? '下架' : '上架' }}
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-pagination
      style="margin-top: 15px"
      background
      layout="total, prev, pager, next"
      :total="total"
      :page-size="listQuery.pageSize"
      :current-page="listQuery.pageNum"
      @current-change="handlePageChange"
    />

    <el-dialog :visible.sync="dialogVisible" :title="dialogTitle" width="500px">
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="书名" prop="title">
          <el-input v-model="form.title" />
        </el-form-item>
        <el-form-item label="作者" prop="author">
          <el-input v-model="form.author" />
        </el-form-item>
        <el-form-item label="出版社" prop="publisher">
          <el-input v-model="form.publisher" />
        </el-form-item>
        <el-form-item label="分类" prop="categoryId">
          <el-select v-model="form.categoryId" placeholder="请选择分类" style="width: 100%">
            <el-option
              v-for="item in categoryList"
              :key="item.id"
              :label="item.name"
              :value="item.id"
            />
          </el-select>
        </el-form-item>

        <el-form-item label="ISBN" prop="isbn">
          <el-input v-model="form.isbn" />
        </el-form-item>
        <el-form-item label="库存" prop="stock">
          <el-input-number v-model="form.stock" :min="0" />
        </el-form-item>
      </el-form>
      <span slot="footer">
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="submitForm">确定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import { getBookPage, addBook, updateBook, updateBookStatus } from '@/api/book'
import { getAllCategories } from '@/api/category'

export default {
  data() {
    return {
      list: [],
      total: 0,
      listLoading: true,
      listQuery: {
        pageNum: 1,
        pageSize: 10,
        keyword: '',
        categoryId: undefined
      },
      categoryList: [],
      dialogVisible: false,
      dialogTitle: '',
      form: {
        id: undefined,
        title: '',
        author: '',
        publisher: '',
        isbn: '',
        stock: 0
      },
      rules: {
        title: [{ required: true, message: '请输入书名', trigger: 'blur' }],
        author: [{ required: true, message: '请输入作者', trigger: 'blur' }],
        categoryId: [{ required: true, message: '请选择分类', trigger: 'change' }]
      }
    }
  },
  created() {
    this.getList()
    this.getCategories()
  },
  methods: {
    async getList() {
      this.listLoading = true
      const res = await getBookPage(this.listQuery)
      this.list = res.data.records
      this.total = res.data.total
      this.listLoading = false
    },
    handleSearch() {
      this.listQuery.pageNum = 1
      this.getList()
    },
    handlePageChange(page) {
      this.listQuery.pageNum = page
      this.getList()
    },

    // 拉取全部分类，喂给搜索栏的下拉框
    async getCategories() {
      const res = await getAllCategories()
      this.categoryList = res.data
    },

    // 上架/下架切换：目标状态和当前状态相反（1↔2）
    handleToggleStatus(row) {
      const targetStatus = row.status === 1 ? 2 : 1
      const actionText = targetStatus === 2 ? '下架' : '上架'
      this.$confirm('确定要' + actionText + '《' + row.title + '》吗？', '提示', { type: 'warning' })
        .then(async() => {
          await updateBookStatus({ id: row.id, status: targetStatus })
          this.$message.success(actionText + '成功')
          this.getList()
        })
        .catch(() => {})
    },

    // 新增：弹窗打开前，把表单洗成空白（防止上一次编辑的残留）
    handleAdd() {
      this.dialogTitle = '新增图书'
      this.form = {
        id: undefined,
        title: '',
        author: '',
        publisher: '',
        categoryId: undefined,
        isbn: '',
        stock: 0
      }
      this.dialogVisible = true
    },

    // 编辑：把行数据"复印"进 form（{ ...row } 浅拷贝，不直接引用 row）
    handleEdit(row) {
      this.dialogTitle = '编辑图书'
      this.form = { ...row }
      this.dialogVisible = true
    },

    // 提交：先校验，再按"有没有 id"决定走新增还是编辑
    submitForm() {
      this.$refs.form.validate(async valid => {
        if (!valid) return
        if (this.form.id) {
          await updateBook(this.form)
          this.$message.success('修改成功')
        } else {
          await addBook(this.form)
          this.$message.success('新增成功')
        }
        this.dialogVisible = false
        this.getList()
      })
    }
  }
}
</script>
