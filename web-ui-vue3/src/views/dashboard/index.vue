<template>
  <ContentWrap>
    <div class="dashboard-text">name: {{ userName }}</div>
    <div class="dashboard-text"><img :src="avatar" alt="" /></div>
  </ContentWrap>
</template>

<script lang="ts" setup>
import { CACHE_KEY, useCache } from '@/hooks/web/useCache'
import avatarImg from '@/assets/imgs/avatar.png'

const { wsCache } = useCache()
const user = wsCache.get(CACHE_KEY.USER)
const avatar = user.avatar ? user.avatar : avatarImg
const userName = user.name ? user.name : 'Admin'
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
</style>
