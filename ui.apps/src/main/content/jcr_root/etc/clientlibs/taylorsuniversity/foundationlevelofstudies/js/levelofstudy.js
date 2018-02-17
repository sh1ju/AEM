$(document).on("foundation-contentloaded", function () {
    "use strict";
    var LVLOFSTUDY = './levelOfStudy', PROG = './programme';
    var levelOfStudy = $('[name="'+LVLOFSTUDY+'"]').closest('.coral3-Select');
    var programme = $('[name="'+PROG+'"]').closest('.coral3-Select');
    var hiddenVal = $('[name="'+PROG+'"]').closest('[type="hidden"]');
    var currLang = "";
    var jsonPath = "/content/tu";

    var programmes = {};

    function getLang($form) {
        var currentPath = $form.attr('action');
        console.log(currentPath);
        if (currentPath) {
            currLang = currentPath.split('/');
            currLang = "/"+currLang[3] + "/"+currLang[4];
            jsonPath += currLang;
        }
    }

    function getProgrammeList() {
        $.getJSON(jsonPath + "/_jcr_content/courseClassifications.json").done(function(data){
            programmes = data.courseClassifications;
        });
    }
    if (levelOfStudy.length > 0) {
        getLang(levelOfStudy.closest('form'));
    } else {
        return;
    }

    function populateProgramme(selectedLevel, selectedProgramme) {
        var select = new Coral.Select().set({
            placeholder: "Choose an item",
            name: "./programme",
            className: "coral3-Select coral-Form-field",
            disabled: (selectedLevel == "")

        });

        if (selectedLevel) {
            _.forEach(programmes, function(k,v) {
                var item = JSON.parse(k);
                if(item.levelOfStudy == selectedLevel) {
                    _.forEach(item.programmes, function(k,v) {
                        select.items.add({
                            content: {
                                innerHTML: k.programme
                            },
                            disabled: false,
                            selected: (selectedProgramme && selectedProgramme == k.programme)
                        });
                    });
                }
            });
        }

        programme.replaceWith(select);
        programme = $('[name="'+PROG+'"]').closest('.coral3-Select');
    }

    levelOfStudy.on('change', function(event) {
        populateProgramme(this.value);
    });
    setTimeout(function() {
        var tmpval;

        hiddenVal.each(function() {
            if (this.value) {
                tmpval = this.value;
            }
        });
        populateProgramme(levelOfStudy.val(), tmpval);
    },250);

    getProgrammeList();
});