<template>
  <div class="home">
    <HelloWorld msg="Welcome to the Issue Management System!" />
  </div>
</template>

<script>
// @ is an alias to /src
import HelloWorld from "@/components/HelloWorld.vue";

export default {
  name: "Home",
  components: {
    HelloWorld
  }
};
</script>

<style scoped>

</style>
