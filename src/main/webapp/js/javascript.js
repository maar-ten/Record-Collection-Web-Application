var laurs = {
    initFeedbackMessagesPanel: function () {
        function verticallyAlign(panel) {
            panel.css("margin-top", panel.outerHeight() / -2 + "px");
        }

        function addCloseButtonBehaviour() {
            $("#feedbackMessagesCloseButton").on("click", function () {
                $(this).parents(".fullscreenOverlay").hide();
            })
        }

        var feedbackMessagePanel = $("#feedbackMessagePanel");
        if (feedbackMessagePanel[0]) {
            verticallyAlign(feedbackMessagePanel);
            addCloseButtonBehaviour();
        }
    },

    initImageSelector: function () {
        var DIRECTION = {
            FORWARD: {
                direction: 1,

                getNextImage: function (imgSelector, currentImg) {
                    var nextImg = currentImg.next();
                    if (!nextImg[0]) {
                        nextImg = imgSelector.find(".img-selector-list-item").first();
                    }
                    return nextImg;
                }
            },
            BACKWARD: {
                direction: -1,

                getNextImage: function (imgSelector, currentImg) {
                    var nextImg = currentImg.prev();
                    if (!nextImg[0]) {
                        nextImg = imgSelector.find(".img-selector-list-item").last();
                    }
                    return nextImg;
                }
            }
        };

        function initializeImages() {
            $(".imgSelector").each(function () {
                var imgList = $(".img-selector-list");

                var checked = imgList.find("input:radio[checked]");
                if (checked[0]) {
                    checked.parents(".img-selector-list-item").addClass("focus");
                    return;
                }
                imgList.children(".img-selector-list-item").first().addClass("focus").find("input:radio").attr("checkedRadios", "checked")
            });
        }

        function moveToNextImg(imgSelector, direction) {
            var currentImg, nextImg, startingPosistion;
            currentImg = imgSelector.find(".img-selector-list-item.focus").removeAttr("style");

            nextImg = direction.getNextImage(imgSelector, currentImg);

            startingPosistion = direction.direction * 100;
            nextImg.css({left: startingPosistion + "%", "z-index": 10}).addClass("focus").animate({left: 0}, 300, function () {
                currentImg.removeClass("focus");
            });
            nextImg.find("input").attr("checked", "checked");
        }

        function addNextImgBehaviour() {
            $(".imgSelector").each(function () {
                    var imgSelector = $(this);
                    imgSelector.find(".selectNextImg").on("click", function () {
                        moveToNextImg(imgSelector, DIRECTION.FORWARD);
                    });
                    imgSelector.find(".selectPrevImg").on("click", function () {
                        moveToNextImg(imgSelector, DIRECTION.BACKWARD);
                    })
                }
            );
        }

        var imageSelector = $(".imgSelector");
        if (imageSelector[0]) {
            initializeImages();
            addNextImgBehaviour();
        }
    }
};

$(window).load(function () {
    laurs.initFeedbackMessagesPanel();
    laurs.initImageSelector();
});
