
<template>

 <div> {{id}} aaaaaaaaaaa </div>
 
</template>

<script>

import { callApi } from "../Api/callApi";
import { required, maxLength } from "vuelidate/lib/validators";

export default {
  name: "AccountUser",
  props:{
    id: {}
  }
}
</script> 