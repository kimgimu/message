var controller = new ScrollMagic.Controller();

var elementsToAnimate = ["#item2", "#item3", "#item4", "#item5", "#item6", "#item7"];
var triggerHook = 0.8;
var duration = "80%";
var offset = 50;

var sceneOptions = {
    triggerHook: triggerHook,
    duration: duration,
    offset: offset
};

// 스크롤 내릴시 item6, item7 영역이 잡히지 않아 임시방편으로 item5가 나타날때 item6,item7번이 순차적으로 나오게 변경  
for (var i = 0; i < elementsToAnimate.length; i++) {
    new ScrollMagic.Scene({
        triggerElement: elementsToAnimate[i]
    })
        .on("enter", function(event) {
            var triggerElement = event.target.triggerElement();
            if (triggerElement) {
                triggerElement.classList.add("visible");
            }
            if (triggerElement.id === "item5") {
                setTimeout(function() {
                    var item6Element = document.querySelector("#item6");
                    if (item6Element) {
                        item6Element.classList.add("visible");
                        setTimeout(function() {
                            var item7Element = document.querySelector("#item7");
                            if (item7Element) {
                                item7Element.classList.add("visible");
                            }
                        }, 500); //item6 visible일 때 item7 visible
                    }
                }, 500); //item5 visible일 때 item6 visible
            }
        })
        .on("leave", function(event) {
            var triggerElement = event.target.triggerElement();
            if (triggerElement) {
                triggerElement.classList.add("visible");
            }
        })
        .addTo(controller);
}


