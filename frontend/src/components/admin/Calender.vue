<template>
  <div class="ui calendar" :id="id_control">
    <div class="ui input left icon" :class="class_input">
      <i class="calendar icon"></i>
      <input
        type="text"
        v-model="calendarVal"
        :placeholder="placeholder"
        :disabled="disabled"
        @click="handle_click"
        @blur="blurInput"
        @focus="isInputActive = true"
      />
    </div>
  </div>
</template>
    
<script>
import moment from "moment";
export default {
  name: "Calender",
  props: {
    value: {},
    type: {
      type: String
    },
    id: {
      type: String
    },
    placeholder: {
      type: String
    },
    formater: {
      type: String
    },
    class_input: {
      type: String
    },
    disabled: {
      type: Boolean,
      default: false
    },
    fomatter_return: {
      type: String
    }
  },

  data() {
    return {
      id_control: "",
      d_formatter: "",
      d_fomatter_server: "",
      calendarVal: "",
      isInputActive: false
    };
  },

  watch: {
    value: function(newValue, oldValue) {
      this.calendarVal = this.value;
    }
  },
  created() {
    this.id_control = this.id ? this.id : `ssv-calendar${this._uid}`;
  },
  mounted() {
    let self = this;
    let formater_default =
      self.type === "date"
        ? "YYYY-MM-DD"
        : self.type === "time"
        ? "HH:mm:ss"
        : "YYYY-MM-DD HH:mm:ss";
    self.d_formatter = self.formater ? self.formater : formater_default;
    this.d_fomatter_server = this.fomatter_return
      ? this.fomatter_return
      : formater_default;
    self.createDatePicker();
  },
  updated() {
    let self = this;
    self.$nextTick(function() {
      if (!self.isInputActive) {
        self.createDatePicker();
      }
    });
  },
  methods: {
    handle_click() {
      let self = this;
      $(`#${self.id_control}`).calendar("popup", "show");
    },
    blurInput() {
      let self = this;
      self.isInputActive = false;
      let date = $(`#${this.id_control}`).calendar("get date");
      if (!date) {
        self.$emit("input", "");
      } else {
        self.$emit("input", moment(date).format(self.d_fomatter_server));
      }
      $(`#${self.id_control}`).calendar("popup", "hide");
    },
    createDatePicker() {
      let self = this;
      let type = self.type ? self.type : "datetime";
      const calendarOpts = {
        type,
        formatter: {
          date(date, settings) {
            if (!date) {
              return "";
            }
            return moment(date).format(self.d_formatter);
          }
        },
        popupOptions: {
          observeChanges: false
        },
        ampm: false
      };
      $(`#${self.id_control}`).calendar(calendarOpts);
      let value = self.calendarVal;
      $(`#${this.id_control}`).calendar(
        "set date",
        new Date(moment(value, self.d_fomatter_server))
      );
    }
  }
};
</script>
