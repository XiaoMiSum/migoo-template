<template>
  <div class="dashboard-container">
    <el-row :gutter="20" type="flex" justify="center">
      <el-col v-for="(o, index) in list[0]" :key="index" :span="5">
        <el-card :body-style="{ padding: '0px' }" shadow="hover">
          <img src="https://shadow.elemecdn.com/app/element/hamburger.9cf7b091-55e9-11e9-a976-7f4d0b07eef6.png" class="image">
          <div style="padding: 14px;">
            <span>{{ '好吃的汉堡0' + index }}</span>
            <div class="bottom clearfix">
              <time class="time">{{ currentDate }}</time>
              <el-button type="text" class="button">操作按钮</el-button>
            </div>
          </div>
        </el-card>
      </el-col>
    </el-row>
    <br>
    <el-row :gutter="20" type="flex" justify="center">
      <el-col v-for="(o, index) in list[1]" :key="index" :span="5">
        <el-card :body-style="{ padding: '0px' }" shadow="hover">
          <img src="https://shadow.elemecdn.com/app/element/hamburger.9cf7b091-55e9-11e9-a976-7f4d0b07eef6.png" class="image">
          <div style="padding: 14px;">
            <span>{{ '好吃的汉堡1' + index }}</span>
            <div class="bottom clearfix">
              <time class="time">{{ currentDate }}</time>
              <el-button type="text" class="button">操作按钮</el-button>
            </div>
          </div>
        </el-card>
      </el-col>
    </el-row>
    <br>
    <el-row :gutter="20" type="flex" justify="center">
      <el-col :span="20" style="text-align: right;">
        <el-pagination
          :hide-on-single-page="false"
          :total="total"
          layout="prev, pager, next"
          :page.sync="queryParams.pageNo"
          :limit.sync="queryParams.pageSize"
          @pagination="getList"
        />
      </el-col>
    </el-row>
  </div>
</template>

<script>

export default {
  name: 'Index',
  data() {
    return {
      list: [[1, 2, 3, 4], [1, 2, 3, 4]],
      currentDate: new Date(),
      total: 200,
      queryParams: {
        pageNo: 1,
        pageSize: 8
      }
    }
  },
  methods: {
    getList() {}
  }
}
</script>

<style lang="scss" scoped>
.dashboard {
  &-container {
    margin: 30px;
  }
  &-text {
    font-size: 30px;
    line-height: 46px;
  }
}

  .time {
    font-size: 13px;
    color: #999;
  }

  .bottom {
    margin-top: 13px;
    line-height: 12px;
  }

  .button {
    padding: 0;
    float: right;
  }

  .image {
    width: 100%;
    display: block;
  }

  .clearfix:before,
  .clearfix:after {
      display: table;
      content: "";
  }

  .clearfix:after {
      clear: both
  }
</style>
