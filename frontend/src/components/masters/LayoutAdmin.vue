<template>
  <div>
    <div class="ui inverted segment">
      <div class="ui inverted secondary pointing menu container">
        <router-link
          class="item"
          :class="isActiveLink('AdminAccount') ? 'active' : ''"
          :to="{ name: 'AdminAccount'}"
        >
          <i class="home icon"></i> Trang chủ
        </router-link>
        <router-link
          class="item"
          :class="isActiveLink('Team') ? 'active' : (isActiveLink('Member') ? 'active': '')"
          :to="{ name: 'Team'}"
        >
          <i class="users icon"></i> Đội
        </router-link>
        <router-link
          class="item"
          :class="isActiveLink('Tournament') ? 'active' : ''"
          :to="{ name: 'Tournament'}"
        >
          <i class="steam icon"></i> Giải đấu
        </router-link>
      </div>
    </div>
    <router-view />
  </div>
</template>

<script>
export default {
  name: "LayoutAdmin",
  methods: {
    isActiveLink(routerName) {
      return this.$route.name == routerName;
    }
  }
};
</script>