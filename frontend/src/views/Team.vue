
<template>
  <div class="ui container" style="margin-top: 25px;">
    <div class="text-title">
      Quản lý đội
    </div>
    <div class="ui buttons">
      <button  class="ui button">Thêm đội</button>
    </div>
    <div style="float:right" class="ui action input">
      <input  type="text" placeholder="Search ..." />
      <button  class="ui button">Search</button>
    </div>
    <table class="ui celled padded table">
      <thead>
        <tr>
          <th>ID</th>
          <th>Tên Team</th>
          <th>Ngày thành lập</th>
          <th>Thông tin thêm</th>
          <th></th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="item in teams" :key="item.id">
          <td>{{item.id}}</td>
          <td class="single line">{{item.name_team}}</td>
          <td>
            <div class="ui yellow rating" data-rating="3" data-max-rating="3">{{item.founding_date}}</div>
          </td>
          <td class="right aligned">{{item.info}}</td>
          <td>
            <div class="compact ui basic icon buttons">
              <button
                class="compact ui button"
                data-position="top right"
                data-variation="mini"
                
              >
                <i class="edit outline icon"></i>
              </button>
              <button
                class="compact ui button"
                data-position="top right"
                data-variation="mini"
                
              >
                <i class="trash alternate outline icon"></i>
              </button>
               <button
                class="compact ui button"
                data-position="top right"
                data-variation="mini"
                @click="manegerMemberRedirect(item.id)"
              >
                <i class="cog icon"></i>
              </button>
            </div>
          </td>
        </tr>
      </tbody>
      <tfoot>
        <tr>
          <th colspan="5">
            <div class="ui right floated pagination menu">
              <a class="icon item">
                <i class="left chevron icon"></i>
              </a>
              <a class="item"></a>
              <a class="icon item" >
                <i class="right chevron icon"></i>
              </a>
            </div>
          </th>
        </tr>
      </tfoot>
    </table>
    <div class="ui modal mini" id="modal-team-delete">
      <div class="ui header red">Delete</div>
      <div class="content">
        <p>Are you want delete?</p>
      </div>
      <div class="actions">
        <div class="ui compact cancel red button">Yes</div>
        <div class="ui compact cancel button">Cancel</div>
      </div>
    </div>
  </div>
</template>

<script>
import { callApi } from "../Api/callApi";
import { required, maxLength, numeric, email } from "vuelidate/lib/validators";
export default {
  name: "Team",
  data() {
    return{
      teams: [],
      totalItem: 0,
      maxPageOnItem: 0,
      pages: 0
    }
  },

  created() {
    this.getListTeam();
  },
  mounted() {
  },

  computed: {

  },

  methods: {
   async getListTeam() {
      let response = await callApi("GET", "admin/team/getall");
      if (response.data.code == "0000") {
        this.teams = response.data.payload;
        this.totalItem = response.data.totalItem;
        this.maxPageOnItem = 5;
        this.pages = response.data.page;
      }
    },
    manegerMemberRedirect(id){
      this.$router.push({ name: "Member", params: { id_team: id } });
    }
  },

  validations() {

  }
};
</script>
