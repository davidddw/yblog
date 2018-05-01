(function () {
    function Validator($element) {
        this.$container = $element;
        this.$bsCalloutInfo = this.$container.find('.bs-callout-info');
        this.$bsCalloutWarnin = this.$container.find('bs-callout-warnin');
    }

    Validator.prototype = {
        constructor: Validator,

        validate: function () {
            this.$container.parsley().validate();
            if (true === this.$container.parsley().isValid()) {
                this.$bsCalloutInfo.removeClass('hidden');
                this.$bsCalloutWarnin.addClass('hidden');
                return true;
            } else {
                this.$bsCalloutInfo.addClass('hidden');
                this.$bsCalloutWarnin.removeClass('hidden');
                return false;
            }
        }
    };
    $.fn.validate = function () {
        //创建Beautifier的实体
        var validator = new Validator(this);
        //调用其方法
        return validator.validate();
    };
})();


$(document).ready(function () {

    $("#button").click(function () {
        if (!$('#loginForm').validate()) {
            return false;
        }
    });
});