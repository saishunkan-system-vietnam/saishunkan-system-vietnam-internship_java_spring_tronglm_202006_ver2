<template>
  <div class="ui container" style="margin-top: 25px;">
    <div style="text-align: center" class="ui header red">Cập nhập kết quả trận thi đấu</div>
    <div class="content">
      <form class="ui form">
        <div class="field">
          <div class="two fields">
            <div class="field" :class="[$v.matchSave.resoultTeamA.$error ? 'field error' : '']">
              <label>{{labelTeamA}}</label>
              <input v-model="matchSave.resoultTeamA" type="number" />
              <template v-if="$v.matchSave.resoultTeamA.$error">
                <span
                  class="ui red text"
                  v-if="!$v.matchSave.resoultTeamA.totalScoredA"
                >Kết quả không trùng khớp!</span>
              </template>
            </div>
            <div class="field" :class="[$v.matchSave.resoultTeamB.$error ? 'field error' : '']">
              <label>{{labelTeamB}}</label>
              <input v-model="matchSave.resoultTeamB" type="number" />
              <template v-if="$v.matchSave.resoultTeamB.$error">
                <span
                  class="ui red text"
                  v-if="!$v.matchSave.resoultTeamB.totalScoredB"
                >Kết quả không trùng khớp!</span>
              </template>
            </div>
          </div>
        </div>

        <div class="field">
          <div class="two fields">
            <div class="field">
              <label>Danh sách thành viên ghi bàn {{labelTeamA}}:</label>
              <div
                style="margin-top:15px"
                class="two fields"
                v-for="(member, index) in matchSave.listPointMemberA"
                :key="index"
              >
                <div
                  class="field"
                  :class="[$v.matchSave.listPointMemberA.$each[index].$error ? 'field error' : '']"
                >
                  <label>Thành viên:</label>
                  <div
                    class="ui fluid selection dropdown"
                    :id="`select-dropdown-member-a-${index}`"
                  >
                    <input type="hidden" />
                    <i class="dropdown icon"></i>
                    <div class="default text">Chọn thành viên</div>
                    <div class="menu">
                      <div
                        v-for="item in memberTeamA"
                        :key="item.id"
                        class="item"
                        :data-value="item.id"
                      >{{ item.nickname }}</div>
                    </div>
                  </div>
                  <template v-if="$v.matchSave.listPointMemberA.$each[index].$error">
                    <span
                      class="ui red text"
                      v-if="!$v.matchSave.listPointMemberA.$each[index].id_member.required"
                    >Không được bỏ trống!</span>
                  </template>
                </div>

                <div class="field">
                  <label>Số bàn ghi được:</label>
                  <input v-model="matchSave.listPointMemberA[index].scored" type="number" />
                </div>
              </div>
              <i @click="pushItemListA" class="plus icon"></i>
              <i @click="minusItemListA" class="minus icon"></i>
            </div>

            <div class="field">
              <label>Danh sách thành viên ghi bàn {{labelTeamB}}:</label>
              <div
                style="margin-top:15px"
                class="two fields"
                v-for="(member, index) in matchSave.listPointMemberB"
                :key="index"
              >
                <div
                  class="field"
                  :class="[$v.matchSave.listPointMemberB.$each[index].$error ? 'field error' : '']"
                >
                  <label>Thành viên:</label>
                  <div
                    class="ui fluid selection dropdown"
                    :id="`select-dropdown-member-b-${index}`"
                  >
                    <input type="hidden" />
                    <i class="dropdown icon"></i>
                    <div class="default text">Chọn thành viên</div>
                    <div class="menu">
                      <div
                        v-for="item in memberTeamB"
                        :key="item.id"
                        class="item"
                        :data-value="item.id"
                      >{{ item.nickname }}</div>
                    </div>
                  </div>
                  <template v-if="$v.matchSave.listPointMemberB.$each[index].$error">
                    <span
                      class="ui red text"
                      v-if="!$v.matchSave.listPointMemberB.$each[index].id_member.required"
                    >Không được bỏ trống!</span>
                  </template>
                </div>
                <div class="field">
                  <label>Số bàn ghi được:</label>
                  <input v-model="matchSave.listPointMemberB[index].scored" type="number" />
                </div>
              </div>
              <i @click="pushItemListB" class="plus icon"></i>
              <i @click="minusItemListB" class="minus icon"></i>
            </div>
          </div>
        </div>

        <div style="margin-top:20px;" class="actions">
          <div v-show="showButonSave" class="ui primary button" @click="saveResultMatchInTnm">
            <i class="save outline icon"></i>Lưu
          </div>
          <div class="ui compact cancel button" @click="cancelSave">
            <i class="stop circle outline icon"></i>Đóng
          </div>
        </div>
      </form>
    </div>
  </div>
</template>

<script>
import { callApi } from "../Api/callApi";
import { required, maxLength, numeric, email } from "vuelidate/lib/validators";

export default {
  name: "ResoultMatch",
  props: ["id_match"],
  data() {
    return {
      id_tournament: "",
      labelTeamA: "",
      labelTeamB: "",
      idEditA: "",
      idEditB: "",
      idResoultTeamA: "",
      idResoultTeamB: "",
      listPointMember: [],
      showButonSave: true,

      dataPointMemberDefault: {
        id_member: "",
        id_match: this.id_match,
        scored: ""
      },

      matchSave: {
        resoultTeamA: "",
        resoultTeamB: "",
        listPointMemberA: [],
        listPointMemberB: []
      },
      lengthListA: 0
    };
  },

  created() {
    this.getMatch();
  },
  mounted() {},

  // watch: {
  //   matchSave: {
  //     handler(val) {
  //       let self = this;
  //         self.matchSave.listPointMemberA.forEach(function(value, i) {
  //           $(`#select-dropdown-member-a-${i}`).dropdown({
  //             onChange(value, text, $choice) {
  //               self.matchSave.listPointMemberA[i].id_member = value;
  //             }
  //           });
  //        });
  //       console.log(self.matchSave.listPointMemberA, "wathc");
  //     },
  //     deep: true
  //   }
  // },

  methods: {
    async getListMemberInTNM() {
      let response = await callApi("GET", "/getall/member-tnm", null, {
        id_tnm: this.tnm_id
      });
      if (response.data.code == "0000") {
        this.listMemberInTNM = response.data.payload;
      }
    },

    pushItemListA() {
      let self = this;
      self.matchSave.listPointMemberA.push({ ...self.dataPointMemberDefault });
      let index = self.matchSave.listPointMemberA.length - 1;
      self.$nextTick(() => {
        $(`#select-dropdown-member-a-${index}`).dropdown({
          onChange(value, text, $choice) {
            self.matchSave.listPointMemberA[index].id_member = value;
          }
        });
      });
    },

    minusItemListA() {
      let index = this.matchSave.listPointMemberA.length - 1;
      this.matchSave.listPointMemberA.splice(index, 1);
    },

    pushItemListB() {
      let self = this;
      self.matchSave.listPointMemberB.push({ ...self.dataPointMemberDefault });
      let index = self.matchSave.listPointMemberB.length - 1;
      self.matchSave.listPointMemberB[index].id_match = self.id_match;

      self.$nextTick(() => {
        $(`#select-dropdown-member-b-${index}`).dropdown({
          onChange(value, text, $choice) {
            self.matchSave.listPointMemberB[index].id_member = value;
          }
        });
      });
    },

    minusItemListB() {
      let index = this.matchSave.listPointMemberB.length - 1;
      this.matchSave.listPointMemberB.splice(index, 1);
    },

    async getListMemberInTeamA() {
      let self = this;
      let response = await callApi("GET", "/getbyteam/member", null, {
        id_team: self.idResoultTeamA
      });
      if (!response) {
        return;
      }
      if (response.data.code == "0000") {
        self.memberTeamA = response.data.payload;
        self.memberTeamA.forEach(element => {
          let pointA = self.listPointMember.find(
            x => x.id_member == element.id
          );
          if (pointA) {
            let pointMatchA = {
              id_match: pointA.id_match,
              id_member: pointA.id_member,
              scored: pointA.scored
            };
            self.matchSave.listPointMemberA.push(pointMatchA);
          }
        });
        if (self.matchSave.listPointMemberA.length > 0) {
          self.$nextTick(() => {
            self.matchSave.listPointMemberA.forEach(function(value, i) {
              $(`#select-dropdown-member-a-${i}`).dropdown(
                "set selected",
                value.id_member
              );
              $(`#select-dropdown-member-a-${i}`).dropdown({
                onChange(value, text, $choice) {
                  self.matchSave.listPointMemberA[i].id_member = value;
                }
              });
            });
          });
        }
      }
    },

    async getListMemberInTeamB() {
      let self = this;
      let response = await callApi("GET", "/getbyteam/member", null, {
        id_team: self.idResoultTeamB
      });
      if (!response) {
        return;
      }
      if (response.data.code == "0000") {
        self.memberTeamB = response.data.payload;
        self.memberTeamB.forEach(element => {
          let pointB = self.listPointMember.find(
            x => x.id_member == element.id
          );
          if (pointB) {
            let pointMatchB = {
              id_match: pointB.id_match,
              id_member: pointB.id_member,
              scored: pointB.scored
            };
            self.matchSave.listPointMemberB.push(pointMatchB);
          }
        });
        if (self.matchSave.listPointMemberB.length > 0) {
          self.$nextTick(() => {
            self.matchSave.listPointMemberB.forEach(function(value, i) {
              $(`#select-dropdown-member-b-${i}`).dropdown(
                "set selected",
                value.id_member
              );
              $(`#select-dropdown-member-b-${i}`).dropdown({
                onChange(value, text, $choice) {
                  self.matchSave.listPointMemberB[i].id_member = value;
                }
              });
            });
          });
        }
      }
    },

    async getMatch() {
      this.showButonSave = true;
      let response = await callApi("GET", "/match/get-match-point", null, {
        id: this.id_match
      });
      if (response.data.code == "0000") {
        this.id_tournament = response.data.payload.teams[0].id_tournament;
        this.labelTeamA = response.data.payload.teams[0].name_team;
        this.labelTeamB = response.data.payload.teams[1].name_team;
        this.idEditA = response.data.payload.listDetailMatch[0].id;
        this.idEditB = response.data.payload.listDetailMatch[1].id;
        this.idResoultTeamA = response.data.payload.listDetailMatch[0].id_team;
        this.idResoultTeamB = response.data.payload.listDetailMatch[1].id_team;
        this.getListMemberInTeamA();
        this.getListMemberInTeamB();
        this.listPointMember = response.data.payload.listPointMemberMatch;

        if (response.data.payload.status_flg == 1) {
          this.matchSave.resoultTeamA =
            response.data.payload.listDetailMatch[0].result;
          this.matchSave.resoultTeamB =
            response.data.payload.listDetailMatch[1].result;
        } else {
          this.matchSave.resoultTeamA = "";
          this.matchSave.resoultTeamB = "";
        }
      }
    },

    async saveResultMatchInTnm() {
      if (this.matchSave.resoultTeamA && this.matchSave.resoultTeamB) {
        this.win_flgA =
          this.matchSave.resoultTeamA > this.matchSave.resoultTeamB
            ? 1
            : this.matchSave.resoultTeamA == this.matchSave.resoultTeamB
            ? 3
            : 2;
        this.win_flgB =
          this.matchSave.resoultTeamB > this.matchSave.resoultTeamA
            ? 1
            : this.matchSave.resoultTeamA == this.matchSave.resoultTeamB
            ? 3
            : 2;
      }
      let listPointMemberMatch = this.matchSave.listPointMemberA.concat(
        this.matchSave.listPointMemberB
      );
      this.$v.matchSave.$touch();
      if (this.$v.matchSave.$invalid) {
        return;
      }
      let response = await callApi("POST", "admin/match/update-all", {
        id: this.id_match,
        status_flg: 1,
        listDetailMatch: [
          {
            id: this.idEditA,
            result: this.matchSave.resoultTeamA,
            win_flg: this.win_flgA
          },
          {
            id: this.idEditB,
            result: this.matchSave.resoultTeamB,
            win_flg: this.win_flgB
          }
        ],
        listPointMemberMatch: listPointMemberMatch
      });
      if (response.data.code == "0000") {
        this.$router.push({
          name: "MatchTnm",
          params: { tnm_id: this.id_tournament }
        });
      }
    },
    cancelSave() {
      this.$router.push({
        name: "MatchTnm",
        params: { tnm_id: this.id_tournament }
      });
    }
  },

  validations() {
    let self = this;
    return {
      matchSave: {
        resoultTeamA: {
          totalScoredA: value => {
            let total = 0;
            self.matchSave.listPointMemberA.forEach(element => {
              total = total + parseInt(element.scored);
            });
            if (value == total) {
              return true;
            }
            return false;
          }
        },
        resoultTeamB: {
          totalScoredB: value => {
            let total = 0;
            self.matchSave.listPointMemberB.forEach(element => {
              total = total + parseInt(element.scored);
            });
            if (value == total) {
              return true;
            }
            return false;
          }
        },
        listPointMemberA: {
          $each: {
            id_member: {
              required
            }
          }
        },
        listPointMemberB: {
          $each: {
            id_member: {
              required
            }
          }
        }
      }
    };
  }
};
</script>
