<template>
  <div>
    <div class="ui inverted segment">
      <div class="ui inverted secondary pointing menu container">
        <router-link
          class="item"
          :class="isActiveLink('AdminHome') ? 'active' : ''"
          :to="{ name: 'AdminHome'}"
        >
          <i class="home icon"></i> Home
        </router-link>
        <router-link
          class="item"
          :class="isActiveLink('Regist') ? 'active' : ''"
          :to="{ name: 'Regist'}"
        >
          <i class="home icon"></i> Regist
        </router-link>
      </div>
    </div>
    <router-view />
  </div>
</template>

<script>
export default {
  name: "LayoutUsers",
  methods: {
    isActiveLink(routerName) {
      return this.$route.name == routerName;
    },
  }
};
</script>