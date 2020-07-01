<template>
  <div class="ui container" style="margin-top: 25px;">
    <button @click="turnTournament">
      <i class="arrow left icon"></i>
    </button>
    <div class="text-title">Quản lý trận đấu</div>
    <table class="team-detail">
      <tr>
        <td>
          <table>
            <tr>
              <td>Tên giải đấu</td>
              <td>{{ tournament.name_tnm }}</td>
            </tr>
            <tr>
              <td>Ngày bắt đầu</td>
              <td>{{ tournament.start_date }}</td>
            </tr>
            <tr>
              <td>Ngày kết thúc</td>
              <td>{{ tournament.end_date }}</td>
            </tr>
            <tr>
              <td>Điểm thắng</td>
              <td>{{ tournament.win_point }}</td>
            </tr>
            <tr>
              <td>Điểm thua</td>
              <td>{{ tournament.lose_point }}</td>
            </tr>
            <tr>
              <td>Điểm hòa</td>
              <td>{{ tournament.equal_point }}</td>
            </tr>
          </table>
        </td>
      </tr>
      <tr>
        <div class="ui segment">
          <td v-html="unencodeInfo_Tnm(tournament.info_tnm)"></td>
        </div>
      </tr>
    </table>

    <div class="text-title">Danh sách trận đấu</div>

    <div class="ui buttons">
      <button @click="addMatchInTnm()" class="ui button">Thêm trận đấu</button>
    </div>
    <table class="ui red table">
      <thead>
        <tr>
          <th>id</th>
          <th>Ngày thi đấu</th>
          <th>Đội A</th>
          <th>Đội B</th>
          <th>Trạng thái</th>
          <th>Điểm</th>
          <th>Chung cuộc</th>
          <th></th>
        </tr>
      </thead>
      <tbody>
        <template v-for="item in listMatch">
          <tr v-if="item.listDetailMatch.length == 2" :key="item.id">
            <td>{{ item.id }}</td>
            <td>{{ getStartTime(item.start_time) }}</td>
            <td>{{ getTeam(item).teamA.name }}</td>
            <td>{{ getTeam(item).teamB.name }}</td>
            <td v-html="getStatus(item.status_flg)"></td>
            <td>{{ getTeam(item).teamA.point }} - {{ getTeam(item).teamB.point }}</td>
            <td>{{ getTeam(item).result }}</td>
            <td>
              <div class="compact ui basic icon buttons">
                <button
                  class="compact ui button"
                  data-position="top right"
                  data-variation="mini"
                  @click="editMatchInTnm(item.id)"
                >
                  <i class="edit outline icon"></i>
                </button>

                <button
                  class="compact ui button"
                  data-position="top right"
                  data-variation="mini"
                  @click="updateMatchInTnm(item.id)"
                >
                  <i class="poll icon"></i>
                </button>
                <button
                  class="compact ui button"
                  data-position="top right"
                  data-variation="mini"
                  @click="deleteMatch(item.id)"
                >
                  <i class="trash alternate outline icon"></i>
                </button>
                <div class="ui button floating icon dropdown match-tournamrnt-status">
                  <i class="exchange alternate icon"></i>
                  <div class="menu">
                    <div
                      :data-value="`${item.id}-0`"
                      class="item"
                      :class="item.status_flg==0 ? 'active' : ''"
                    >Chưa thi đấu</div>
                    <div
                      :data-value="`${item.id}-1`"
                      class="item"
                      :class="item.status_flg==1 ? 'active' : ''"
                    >Đã thi đấu</div>
                    <div
                      :data-value="`${item.id}-2`"
                      class="item"
                      :class="item.status_flg==2 ? 'active' : ''"
                    >Đang thi đấu</div>
                  </div>
                </div>
              </div>
            </td>
          </tr>
        </template>
      </tbody>
      <tfoot>
        <div class="ui right floated pagination menu"></div>
      </tfoot>
    </table>
    <div class="ui modal large" id="modal-match-tournnament-add">
      <div v-if="idWatch" class="ui header red">Sửa lịch thi đấu</div>
      <div v-else class="ui header red">Thêm lịch thi đấu</div>
      <div class="content">
        <form class="ui form">
          <div class="field" :class="[$v.dateTimeLocal.$error ? 'field error' : '']">
            <label>Thời gian bắt đầu</label>
            <input v-model="dateTimeLocal" type="datetime-local" />
            <template v-if="$v.dateTimeLocal.$error">
              <span class="ui red text" v-if="!$v.dateTimeLocal.required">Không được bỏ trống!</span>
            </template>
          </div>
          <div class="field" :class="[$v.idTeamA.$error ? 'field error' : '']">
            <div class="ui sub header">Đội A</div>
            <div class="ui fluid selection dropdown" id="select-dropdown-team-a">
              <input type="hidden" name="user" />
              <i class="dropdown icon"></i>
              <div class="default text">Chọn đội</div>
              <div class="menu">
                <div
                  v-for="item in teams"
                  :key="item.id"
                  class="item"
                  :data-value="item.id"
                >{{ item.name_team }}</div>
              </div>
            </div>
            <template v-if="$v.idTeamA.$error">
              <span class="ui red text" v-if="!$v.idTeamA.required">Không được bỏ trống!</span>
            </template>
          </div>
          <div class="field" :class="[$v.idTeamB.$error ? 'field error' : '']">
            <div class="ui sub header">Đội B</div>
            <div class="ui fluid selection dropdown" id="select-dropdown-team-b">
              <input type="hidden" name="user" />
              <i class="dropdown icon"></i>
              <div class="default text">Chọn đội</div>
              <div class="menu">
                <div
                  v-for="item in teams"
                  :key="item.id"
                  class="item"
                  :data-value="item.id"
                >{{ item.name_team }}</div>
              </div>
            </div>
            <template v-if="$v.idTeamB.$error">
              <span class="ui red text" v-if="!$v.idTeamB.required">Không được bỏ trống!</span>
              <span class="ui red text" v-if="!$v.idTeamB.isVald">Đội B phải khác đội A</span>
            </template>
          </div>
          <div class="field" :class="[$v.match.place_match.$error ? 'field error' : '']">
            <label>Địa điểm thi đấu</label>
            <input v-model="match.place_match" type="text" />
            <template v-if="$v.match.place_match.$error">
              <span class="ui red text" v-if="!$v.match.place_match.required">Không được bỏ trống!</span>
            </template>
          </div>
          <div style="margin-top:20px;" class="actions">
            <div v-show="showButonSave" class="ui primary button" @click="saveMatchInTnm">
              <i class="save outline icon"></i>Lưu
            </div>
            <div class="ui compact cancel button">
              <i class="stop circle outline icon"></i>Đóng
            </div>
          </div>
        </form>
      </div>
    </div>

    <div class="ui modal" id="modal-resoult-match">
      <div class="ui header red">Cập nhập kết quả trận thi đấu</div>
      <div class="content">
        <form class="ui form">
          <div class="field">
            <div class="two fields">
              <div class="field" :class="[$v.resoultTeamA.$error ? 'field error' : '']">
                <label>{{labelTeamA}}</label>
                <input v-model="resoultTeamA" type="number" />
                <template v-if="$v.resoultTeamA.$error">
                  <span class="ui red text" v-if="!$v.resoultTeamA.required">Không được bỏ trống!</span>
                </template>
              </div>
              <div class="field" :class="[$v.resoultTeamB.$error ? 'field error' : '']">
                <label>{{labelTeamB}}</label>
                <input v-model="resoultTeamB" type="number" />
                <template v-if="$v.resoultTeamB.$error">
                  <span class="ui red text" v-if="!$v.resoultTeamB.required">Không được bỏ trống!</span>
                </template>
              </div>
            </div>
          </div>
          <div style="margin-top:20px;" class="actions">
            <div v-show="showButonSave" class="ui primary button" @click="saveResultMatchInTnm">
              <i class="save outline icon"></i>Lưu
            </div>
            <div class="ui compact cancel button">
              <i class="stop circle outline icon"></i>Đóng
            </div>
          </div>
        </form>
      </div>
    </div>
    <div class="ui modal mini" id="modal-delete-match-tournament">
      <div class="ui header red">Delete</div>
      <div class="content">
        <p>Bạn có muốn xóa trận đấu này khỏi giải đấu ?</p>
      </div>
      <div class="actions">
        <div @click="deleteHandleMatchInTnm()" class="ui compact red button">Yes</div>
        <div class="ui compact cancel button">Cancel</div>
      </div>
    </div>
  </div>
</template>

<script>
import { callApi } from "../Api/callApi";
import { required, maxLength, numeric, email } from "vuelidate/lib/validators";
import Calender from "../components/admin/Calender";
import moment from "moment";

export default {
  name: "MatchTnm",
  props: ["tnm_id"],
  data() {
    return {
      requestDataSaveTeam: {
        id: this.tnm_id,
        listTeam: []
      },
      teams: [],
      listTeam: [],
      members: [],
      tournament: {},
      idTeamA: "",
      idTeamB: "",
      dateTimeLocal: "",
      labelTeamB: "",
      labelTeamA: "",
      match: {
        id: null,
        str_start_time: "",
        place_match: "",
        listDetailMatch: []
      },
      listMatch: [],
      detail_match: {},
      idEditA: "",
      idEditB: "",
      idWatch: null,
      showButonSave: true,
      resoultTeamA: "",
      resoultTeamB: "",
      win_flgA: 0,
      win_flgB: 0
    };
  },

  components: {
    Calender
  },

  created() {
    this.getListMatch();
    this.getListTeamInTnm();
    this.getInfoTournament();
  },

  mounted() {
    let self = this;
    $("#select-dropdown-team-a").dropdown({
      onChange(value, text, $choice) {
        self.idTeamA = value;
      }
    });
    $("#select-dropdown-team-b").dropdown({
      onChange(value, text, $choice) {
        self.idTeamB = value;
      }
    });
  },

  methods: {
    async changeStatus(n) {},

    getTeam(item) {
      let teamA = item.listDetailMatch[0];
      let teamB = { ...item.listDetailMatch[1] };
      let infoTeamA = [...this.teams].find(x => x.id == teamA.id_team);
      let infoTeamB = [...this.teams].find(x => x.id == teamB.id_team);

      let pointTeamA = item.status_flg ? teamA.result : "";
      let pointTeamB = item.status_flg ? teamB.result : "";

      let result = "";
      if (item.status_flg) {
        result =
          pointTeamA > pointTeamB
            ? "W - L"
            : pointTeamA == pointTeamB
            ? "N - N"
            : "L - W";
      }
      let infoDetail = {
        teamA: {
          name: infoTeamA ? infoTeamA.name_team : "",
          point: pointTeamA
        },
        teamB: {
          name: infoTeamB ? infoTeamB.name_team : "",
          point: pointTeamB
        },
        result: result
      };
      return infoDetail;
    },
    getStatus(sta_flg) {
      if (sta_flg == 0) {
        return '<i style="height: 17px" class="ui green text play icon "></i> Chưa thi đấu';
      }
      if (sta_flg == 2) {
        return '<i style="height: 17px" class="ui yellow text video icon "></i> Đang thi đấu';
      }
      return '<i style="height: 17px" class="ui red text pause icon "></i> Đã thi đấu';
    },

    getStartTime(time) {
      if (time) {
        return moment(time).format("YYYY-MM-DD HH:mm");
      }
      return;
    },

    addMatchInTnm() {
      this.refestData();
      this.showButonSave = true;
      this.idWatch = null;
      $("#modal-match-tournnament-add").modal("show");
    },

    unencodeInfo_Tnm(info_tnmEncode) {
      return unescape(info_tnmEncode);
    },

    refestData() {
      this.$v.$reset();
      this.match.id = null;
      this.dateTimeLocal = null;
      this.match.place_match = "";
      $("#select-dropdown-team-a").dropdown("set text", null);
      $("#select-dropdown-team-b").dropdown("set text", null);
      this.idTeamA = null;
      this.idTeamB = null;
    },

    async editMatchInTnm(id) {
      this.showButonSave = true;
      this.idWatch = 1;
      let response = await callApi("GET", "/match/get-match", null, {
        id: id,
        id_tournament: this.tnm_id
      });
      if (response.data.code == "0000") {
        this.detail_match = response.data.payload;
        $("#select-dropdown-team-a").dropdown(
          "set selected",
          this.detail_match.teams[0].id
        );
        $("#select-dropdown-team-b").dropdown(
          "set selected",
          this.detail_match.teams[1].id
        );
        this.match.id = this.detail_match.id;
        this.dateTimeLocal = this.detail_match.start_time;
        this.match.place_match = this.detail_match.place_match;
        this.idEditA = this.detail_match.listDetailMatch[0].id;
        this.idEditB = this.detail_match.listDetailMatch[1].id;

        $("#modal-match-tournnament-add").modal("show");
      }
    },

    async getListMatch() {
      let self = this;
      let response = await callApi("GET", "/match/get-all", null, {
        id: this.tnm_id
      });
      if (response.data.code == "0000") {
        this.listMatch = response.data.payload;
      }
      this.$nextTick( () => {
        $(".match-tournamrnt-status").dropdown({
          async onChange(value, text, $choice) {
            let index = value.indexOf("-");
            let idMatch = value.substring(0, index);
            let statusMatch = value.substring(index+1, value.length);
            let response = await callApi("POST", "/admin/match_one/update", {
              id: idMatch,
              status_flg: statusMatch
            });
            if(response.data.code == "0000"){
              self.getListMatch();
            }
          }
        });
      });
    },

    async deleteMatch(id) {
      let response = await callApi("GET", "/match/get-match", null, {
        id: id,
        id_tournament: this.tnm_id
      });
      if (response.data.code == "0000") {
        this.match.id = response.data.payload.id;
        this.idEditA = response.data.payload.listDetailMatch[0].id;
        this.idEditB = response.data.payload.listDetailMatch[1].id;
        $("#modal-delete-match-tournament").modal("show");
      } else {
        this.getListMatch();
      }
    },

    async deleteHandleMatchInTnm() {
      let response = await callApi("POST", "admin/match/update", {
        id: this.match.id,
        del_flg: 1,
        listDetailMatch: [
          {
            id: this.idEditA,
            del_flg: 1
          },
          {
            id: this.idEditB,
            del_flg: 1
          }
        ]
      });
      if (response.data.code == "0000") {
        this.getListMatch();
        $("#modal-delete-match-tournament").modal("hide");
      }
    },

    async getListTeamInTnm() {
      let response = await callApi("GET", "team/gettournament", null, {
        id_tournament: this.tnm_id
      });
      if (!response.data.code) {
        return;
      }
      if (response.data.code == "0000") {
        this.teams = response.data.payload;
      }
    },

    async updateMatchInTnm(id) {
      this.showButonSave = true;
      let response = await callApi("GET", "/match/get-match", null, {
        id: id,
        id_tournament: this.tnm_id
      });
      if (response.data.code == "0000") {
        this.match.id = response.data.payload.id;
        this.labelTeamA = response.data.payload.teams[0].name_team;
        this.labelTeamB = response.data.payload.teams[1].name_team;
        this.idEditA = response.data.payload.listDetailMatch[0].id;
        this.idEditB = response.data.payload.listDetailMatch[1].id;
        if (response.data.payload.status_flg == 1) {
          this.resoultTeamA = response.data.payload.listDetailMatch[0].result;
          this.resoultTeamB = response.data.payload.listDetailMatch[1].result;
        } else {
          this.resoultTeamA = "";
          this.resoultTeamB = "";
        }
        $("#modal-resoult-match").modal("show");
      } else {
        this.getListMatch();
      }
    },
    async saveResultMatchInTnm() {
      if (this.resoultTeamA && this.resoultTeamB) {
        this.win_flgA =
          this.resoultTeamA > this.resoultTeamB
            ? 1
            : this.resoultTeamA == this.resoultTeamB
            ? 3
            : 2;
        this.win_flgB =
          this.resoultTeamB > this.resoultTeamA
            ? 1
            : this.resoultTeamA == this.resoultTeamB
            ? 3
            : 2;
      }
      this.$v.resoultTeamA.$touch();
      if (this.$v.resoultTeamA.$invalid) {
        return;
      }
      this.$v.resoultTeamB.$touch();
      if (this.$v.resoultTeamB.$invalid) {
        return;
      }
      let response = await callApi("POST", "admin/match/update", {
        id: this.match.id,
        status_flg: 1,
        listDetailMatch: [
          {
            id: this.idEditA,
            result: this.resoultTeamA,
            win_flg: this.win_flgA
          },
          {
            id: this.idEditB,
            result: this.resoultTeamB,
            win_flg: this.win_flgB
          }
        ]
      });
      if (response.data.code == "0000") {
        this.getListMatch();
        this.showButonSave = false;
        $("#modal-resoult-match").modal("hide");
      }
    },

    async saveMatchInTnm() {
      let response;
      this.$v.idTeamA.$touch();
      if (this.$v.idTeamA.$invalid) {
        return;
      }
      this.$v.idTeamB.$touch();
      if (this.$v.idTeamB.$invalid) {
        return;
      }
      this.$v.dateTimeLocal.$touch();
      if (this.$v.dateTimeLocal.$invalid) {
        return;
      }
      this.$v.match.$touch();
      if (this.$v.match.$invalid) {
        return;
      }

      if (!this.idWatch) {
        this.match.str_start_time = moment(this.dateTimeLocal).format(
          "YYYY-MM-DD HH:mm"
        );
        response = await callApi("POST", "admin/match/create", {
          str_start_time: this.match.str_start_time,
          place_match: this.match.place_match,
          listDetailMatch: [
            {
              id_team: this.idTeamA
            },
            {
              id_team: this.idTeamB
            }
          ]
        });
      } else {
        this.match.listDetailMatch = [
          { id: this.idEditA, id_team: this.idTeamA },
          {
            id: this.idEditB,
            id_team: this.idTeamB
          }
        ];
        this.match.str_start_time = moment(this.dateTimeLocal).format(
          "YYYY-MM-DD HH:mm"
        );
        response = await callApi("POST", "admin/match/update", this.match);
      }

      if (!response.data.code) {
        return;
      }
      if (response.data.code == "0000") {
        this.getListMatch();
        this.showButonSave = false;
        $("#modal-match-tournnament-add").modal("hide");
      }
    },

    async getInfoTournament() {
      let response = await callApi("GET", "/tournament/getbyid", null, {
        id: this.tnm_id
      });
      if (response.data.code == "0000") {
        this.tournament = response.data.payload;
      }
    },

    sussefull(mess) {
      $("body").toast({
        class: "success",
        message: mess
      });
    },

    turnTournament() {
      this.$router.push({ name: "Tournament" });
    }
  },
  validations() {
    let self = this;
    return {
      resoultTeamA: {
        required
      },
      resoultTeamB: {
        required
      },

      idTeamA: {
        required
      },
      idTeamB: {
        isVald: value => {
          if (value == self.idTeamA) {
            return false;
          }
          return true;
        },
        required
      },
      dateTimeLocal: {
        required
      },
      match: {
        place_match: {
          required
        }
      }
    };
  }
};
</script>
