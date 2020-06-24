
<template>

 <div> 
   <button @click="logout()">Logout</button>
  </div>
 
</template>

<script>

import { callApi } from "../Api/callApi";
import { required, maxLength } from "vuelidate/lib/validators";

export default {
  name: "AccountUser",
  props:{
    id: {}
  },
  methods: {
    async logout(){
      let response = await callApi(
          "GET",
          "logout"
        );
    }
  },
}
</script> 