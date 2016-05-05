

$(function () {
    window.onload = function () {

        $.ajax({
            type: "GET",
            url: "jgaliweather_api/v1/meteorologicalData/27027",
            success: function (data, textStatus, response) {

                // SkyState
                $("#skMorning1").append(data.sky[0]);
                $("#skAfternoon1").append(data.sky[1]);
                $("#skNight1").append(data.sky[2]);

                $("#skMorning2").append(data.sky[3]);
                $("#skAfternoon2").append(data.sky[4]);
                $("#skNight2").append(data.sky[5]);

                $("#skMorning3").append(data.sky[6]);
                $("#skAfternoon3").append(data.sky[7]);
                $("#skNight3").append(data.sky[8]);

                $("#skMorning4").append(data.sky[9]);
                $("#skAfternoon4").append(data.sky[10]);
                $("#skNight4").append(data.sky[11]);

                // WIND
                $("#windMorning1").append(data.wind[0]);
                $("#windAfternoon1").append(data.wind[1]);
                $("#windNight1").append(data.wind[2]);

                $("#windMorning2").append(data.wind[3]);
                $("#windAfternoon2").append(data.wind[4]);
                $("#windNight2").append(data.wind[5]);

                $("#windMorning3").append(data.wind[6]);
                $("#windAfternoon3").append(data.wind[7]);
                $("#windNight3").append(data.wind[8]);

                $("#windMorning4").append(data.wind[9]);
                $("#windAfternoon4").append(data.wind[10]);
                $("#windNight4").append(data.wind[11]);

                //Temperature
                $("#tempMin1").append(data.temp[0]);
                $("#tempMax1").append(data.temp[1]);

                $("#tempMin2").append(data.temp[2]);
                $("#tempMax2").append(data.temp[3]);

                $("#tempMin3").append(data.temp[4]);
                $("#tempMax3").append(data.temp[5]);

                $("#tempMin4").append(data.temp[6]);
                $("#tempMax4").append(data.temp[7]);
                
                //Comment
                $("#comment").append(data.comment);

            },
            error: function (response, textStatus, errorThrown) {
                $("#comment").append("ERROR: " + errorThrown);
            }
        });
    };
});