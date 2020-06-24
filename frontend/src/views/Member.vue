
<template>
  <div class="ui container" style="margin-top: 25px;">
    <div class="text-title">
      Quản lý thành viên của đội
    </div>
    <table class="team-detail">
      <tr>
        <td>
          <table>
            <tr>
              <td>Tên Đội</td>
              <td>{{team.name_team}}</td>
            </tr>
             <tr>
              <td>Ngày thành lập</td>
              <td>{{team.founding_date}}</td>
            </tr>
             <tr>
              <td>Mô tả</td>
              <td>{{team.info}}</td>
            </tr>
          </table>
        </td>
        <td>yhugyu</td>
      </tr>
    </table>
    <div class="ui buttons">
      <button class="ui button">Thêm đội</button>
    </div>
    <table class="ui celled padded table">
      <thead>
        <tr>
          <th>ID</th>
          <th>Tên Thành viên</th>
          <th>Tên biệt danh</th>
          <th></th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="item in members" :key="item.id">
          <td>{{item.id}}</td>
          <td class="single line">{{item.name_member}}</td>
          <td>
            <div class="ui yellow rating" data-rating="3" data-max-rating="3">{{item.nickname}}</div>
          </td>
          <td>
            <div class="compact ui basic icon buttons">
              <button class="compact ui button" data-position="top right" data-variation="mini">
                <i class="edit outline icon"></i>
              </button>
              <button class="compact ui button" data-position="top right" data-variation="mini">
                <i class="trash alternate outline icon"></i>
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
              <a class="icon item">
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
  name: "Member",
  props: ["id_team"],
  data() {
    return {
      members: [],
      team: {},
      totalItem: 0,
      maxPageOnItem: 0,
      pages: 0
    };
  },

  created() {
    this.getListMembers();
    this.getInfoTeam();
  },
  mounted() {},

  computed: {},

  methods: {
    async getListMembers() {
      let response = await callApi("GET", "admin/getbyteam/member", null, {
        id_team: this.id_team
      });

      if (response.data.code != "0000") {
        return this.$router.push({ name: "Team" });
      }

      this.members = response.data.payload;
      this.totalItem = response.data.totalItem;
      this.maxPageOnItem = 5;
      this.pages = response.data.page;
    },
    async getInfoTeam(){
      let response = await callApi("GET", "admin/team/getbyid", null, {id: this.id_team});
      if(response.data.code == "0000"){
        this.team = response.data.payload;
      }
      console.log(response.data.payload, "name")
    },
  },


  validations() {}
};
</script>
