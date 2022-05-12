<template>
  <div class="app-container">
    <el-row :gutter="20">
      <!--部门数据-->
      <el-col :span="4" :xs="24">
        <div class="head-container">
          <el-input v-model="deptName" placeholder="请输入部门名称" clearable size="small" prefix-icon="el-icon-search" style="margin-bottom: 20px" />
        </div>
        <div class="head-container">
          <el-tree
            ref="tree"
            :data="deptOptions"
            :props="defaultProps"
            :expand-on-click-node="false"
            :filter-node-method="filterNode"
            default-expand-all
            @node-click="handleNodeClick"
          />
        </div>
      </el-col>
      <!--用户数据-->
      <el-col :span="20" :xs="24">
        <el-form v-show="showSearch" ref="queryForm" :model="queryParams" :inline="true" label-width="68px">
          <el-form-item label="员工姓名" prop="name">
            <el-input
              v-model="queryParams.name"
              placeholder="请输入员工姓名"
              clearable
              size="small"
              style="width: 240px"
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
          <el-form-item label="手机号码" prop="phone">
            <el-input
              v-model="queryParams.phone"
              placeholder="请输入手机号码"
              clearable
              size="small"
              style="width: 240px"
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
          <!--           <el-form-item label="状态" prop="status">
            <el-select v-model="queryParams.status" placeholder="用户状态" clearable size="small" style="width: 240px">
              <el-option v-for="dict in statusDictDatas" :key="parseInt(dict.value)" :label="dict.label" :value="parseInt(dict.value)"/>
            </el-select>
          </el-form-item>
          <el-form-item label="创建时间">
            <el-date-picker v-model="dateRange" size="small" style="width: 240px" value-format="yyyy-MM-dd" type="daterange"
              range-separator="-" start-placeholder="开始日期" end-placeholder="结束日期"></el-date-picker>
          </el-form-item> -->
          <el-form-item>
            <el-button type="cyan" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
            <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
          </el-form-item>
        </el-form>

        <el-row :gutter="10" class="mb8">
          <el-col :span="1.5">
            <el-button
              type="primary"
              icon="el-icon-plus"
              size="mini"
              @click="handleAdd"
            >新增</el-button>
          </el-col>
          <right-toolbar :show-search.sync="showSearch" @queryTable="getList" />
        </el-row>

        <el-table v-loading="loading" :data="userList">
          <el-table-column label="编号" align="center" prop="id" width="50" />
          <el-table-column label="手机号码" align="left" prop="phone" :show-overflow-tooltip="true" />
          <el-table-column label="姓名" align="left" prop="name" :show-overflow-tooltip="true" />
          <el-table-column label="部门" align="left" prop="dept.name" :show-overflow-tooltip="true" />
          <el-table-column label="性别" align="center" width="50">
            <template slot-scope="scope">
              <span>{{ scope.row.gender | filterGender }}</span>
            </template>
          </el-table-column>
          <el-table-column label="状态" align="center" width="100">
            <template slot-scope="scope">
              <el-switch v-model="scope.row.status" :active-value="1" :inactive-value="0" @change="handleStatusChange(scope.row)" />
            </template>
          </el-table-column>
          <el-table-column label="创建时间" align="center" prop="createTime" width="160">
            <template slot-scope="scope">
              <span>{{ parseTime(scope.row.createTime) }}</span>
            </template>
          </el-table-column>
          <el-table-column label="操作" align="center" width="160" class-name="small-padding fixed-width">
            <template slot-scope="scope">
              <el-button size="large" type="text" icon="el-icon-edit" @click="handleUpdate(scope.row)">修改</el-button>
              <el-dropdown @command="(command) => handleCommand(command, scope.$index, scope.row)">
                <span class="el-dropdown-link">
                  更多操作<i class="el-icon-arrow-down el-icon--right" />
                </span>
                <el-dropdown-menu slot="dropdown">
                  <el-dropdown-item v-if="scope.row.id !== 1" command="handleDelete" size="mini" type="text" icon="el-icon-delete">删除</el-dropdown-item>
                  <el-dropdown-item command="handleResetPwd" size="mini" type="text" icon="el-icon-key">重置密码</el-dropdown-item>
                </el-dropdown-menu>
              </el-dropdown>
            </template>
          </el-table-column>
        </el-table>

        <pagination
          v-show="total>0"
          :total="total"
          :page.sync="queryParams.pageNo"
          :limit.sync="queryParams.pageSize"
          @pagination="getList"
        />
      </el-col>
    </el-row>

    <!-- 添加或修改参数配置对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="600px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-row>
          <el-col>
            <el-form-item label="员工姓名" prop="name">
              <el-input v-model="form.name" placeholder="请输入员工姓名" />
            </el-form-item>
          </el-col>
        </el-row>

        <el-row>
          <el-col>
            <el-form-item label="手机号" prop="phone">
              <el-input v-model="form.phone" placeholder="请输入手机号码" :maxlength="11" />
            </el-form-item>
          </el-col>
        </el-row>

        <el-row>
          <el-col>
            <el-form-item label="所属部门" prop="deptId">
              <treeselect
                v-model="form.deptId"
                :options="deptOptions"
                :show-count="true"
                placeholder="请选择归属部门"
                :normalizer="normalizer"
              />
            </el-form-item>
          </el-col>
        </el-row>

        <el-row>
          <el-col>
            <el-form-item label="电子邮箱" prop="email">
              <el-input v-model="form.email" placeholder="请输入电子邮箱" />
            </el-form-item>
          </el-col>
        </el-row>

        <el-row>
          <el-col>
            <el-form-item label="性别" prop="gender">
              <el-select v-model="form.gender" placeholder="请选择性别" style="width: 100%">
                <el-option v-for="(item, index) in genderOptions" :key="index" :value="item.value" :label="item.label" />
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row>
          <el-col>
            <el-form-item v-if="form.id === undefined" label="登录密码" prop="password">
              <el-input v-model="form.password" placeholder="请输入登录密码" type="password" />
            </el-form-item>
          </el-col>
        </el-row>

      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

  </div>
</template>

<script>
import {
  addUser,
  delUser,
  getUser,
  listUser,
  resetUserPwd,
  updateUser
} from '@/api/manager/user'

import { listSimple as listSimpleDepts } from '@/api/manager/dept'

import Treeselect from '@riophae/vue-treeselect'
import '@riophae/vue-treeselect/dist/vue-treeselect.css'

export default {
  name: 'User',
  components: { Treeselect },
  filters: {
    filterGender(value) {
      return value === 0 ? '女' : value === 1 ? '男' : '-'
    }
  },
  data() {
    return {
      // 遮罩层
      loading: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 用户表格数据
      userList: null,
      // 弹出层标题
      title: '',
      // 是否显示弹出层
      open: false,
      // 日期范围
      dateRange: [],
      // 表单参数
      form: {},
      // 查询参数
      queryParams: {
        pageNo: 1,
        pageSize: 10,
        name: undefined,
        phone: undefined,
        status: undefined,
        deptId: undefined
      },
      // 表单校验
      rules: {
        name: [
          { required: true, message: '用户姓名不能为空', trigger: 'blur' }
        ],
        password: [
          { required: true, message: '用户密码不能为空', trigger: 'blur' }
        ],
        phone: [
          {
            required: true,
            pattern: /^1[3|4|5|6|7|8|9][0-9]\d{8}$/,
            message: '请输入正确的手机号码',
            trigger: 'blur'
          }
        ]
      },
      // 是否显示弹出层（角色权限）
      openRole: false,
      // 部门
      deptName: '',
      deptOptions: [],
      defaultProps: {
        children: 'children',
        label: 'name'
      },
      // 性别下拉选项
      genderOptions: [{ value: 0, label: '女' }, { value: 1, label: '男' }]
    }
  },
  created() {
    this.getList()
    this.getTreeselect()
  },
  methods: {
    // 更多操作
    handleCommand(command, index, row) {
      switch (command) {
        case 'handleUpdate':
          this.handleUpdate(row)// 修改客户信息
          break
        case 'handleDelete':
          this.handleDelete(row)// 红号变更
          break
        case 'handleResetPwd':
          this.handleResetPwd(row)
          break
        default:
          break
      }
    },
    /** 查询用户列表 */
    getList() {
      this.loading = true
      listUser(this.queryParams).then(response => {
        this.userList = response.data.list
        this.total = response.data.total
        this.loading = false
      }
      )
    },
    /** 查询部门下拉树结构 + 岗位下拉 */
    getTreeselect() {
      listSimpleDepts().then(response => {
        // 处理 deptOptions 参数
        this.deptOptions = []
        this.deptOptions.push(...this.handleTree(response.data, 'id'))
      })
    },
    // 筛选节点
    filterNode(value, data) {
      if (!value) return true
      return data.name.indexOf(value) !== -1
    },
    // 节点单击事件
    handleNodeClick(data) {
      this.queryParams.deptId = data.id
      this.getList()
    },
    // 用户状态修改
    handleStatusChange(row) {
      const text = row.status === 1 ? '启用' : '禁用'
      this.$confirm('确认要"' + text + '"用户「' + row.name + '」吗?', '警告', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(function() {
        return updateUser({ id: row.id, status: row.status })
      }).then(() => {
        this.msgSuccess(text + '成功')
      }).catch(function() {
        row.status = row.status === 1 ? 0 : 1
      })
    },
    // 取消按钮
    cancel() {
      this.open = false
      this.reset()
    },
    // 取消按钮（角色权限）
    cancelRole() {
      this.openRole = false
      this.reset()
    },
    // 表单重置
    reset() {
      this.form = {
        id: undefined,
        deptId: undefined,
        name: undefined,
        phone: undefined,
        password: undefined,
        email: undefined,
        gender: undefined,
        status: 1,
        remark: undefined,
        postIds: []
      }
      this.resetForm('form')
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNo = 1
      this.getList()
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.dateRange = []
      this.resetForm('queryForm')
      this.handleQuery()
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset()
      // 打开表单，并设置初始化
      this.open = true
      this.title = '添加用户'
      this.form.password = ''
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      const id = row.id
      getUser(id).then(response => {
        this.form = response.data
        this.open = true
        this.title = '修改用户'
        this.form.password = ''
      })
    },
    /** 重置密码按钮操作 */
    handleResetPwd(row) {
      this.$prompt('请输入「' + row.name + '」的新密码', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消'
      }).then(({ value }) => {
        resetUserPwd(row.id, value).then(() => {
          this.msgSuccess('修改成功，新密码是：' + value)
        })
      }).catch(() => {})
    },
    /** 提交按钮 */
    submitForm: function() {
      this.$refs['form'].validate(valid => {
        if (valid) {
          if (this.form.id !== undefined) {
            updateUser(this.form).then(() => {
              this.msgSuccess('修改成功')
              this.open = false
              this.getList()
            })
          } else {
            addUser(this.form).then(() => {
              this.msgSuccess('新增成功')
              this.open = false
              this.getList()
            })
          }
        }
      })
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids
      this.$confirm('是否确认删除用户「' + row.name + '」?', '警告', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(function() {
        return delUser(ids)
      }).then(() => {
        this.getList()
        this.msgSuccess('删除成功')
      })
    },
    // 格式化部门的下拉框
    normalizer(node) {
      return {
        id: node.id,
        label: node.name,
        children: node.children
      }
    }
  }
}
</script>
<style>
  .el-dropdown-link {
    cursor: pointer;
    color: #1890ff;
    margin-left: 5px;
  }
  .el-icon-arrow-down {
    font-size: 14px;
  }
</style>
