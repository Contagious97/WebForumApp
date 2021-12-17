import jQuery, { data } from "jquery"
import $ from "jquery"
import {Chart, registerables} from 'chart.js';
import React, {useRef, useEffect, useState} from "react";
import {ChartDataSource} from "chartjs-plugin-datasource"
//import {defined} from "chart.js/helpers";
import './Chart.css'
import axios from "axios";


const [dataFromServer, setDataFromServer] = useState();
var loading = true;
async function getData(){
    let axiosConfig = {headers:{'Content-Type':'application/json'}}
    let url = "http://localhost:8890/charts/12"
    await axios.get(url,axiosConfig)
    .then((json) => {
        //console.log(json)
        let final = JSON.parse(json.data.data)
        setDataFromServer(final)

    }).finally(
        loading = false)
    //console.log(dataFromServer)
}

export default function Charts() {

    

    

    if(loading)
        getData();

        let myChart;

    if(!loading)
    {


    //console.log(dataFromServer)
    
    var d = {x:[1,2,3,4,6,7],y:[5,6,7,8,9,12,16]};
    var chartColors = {
        red: 'rgb(255, 99, 132)',
        blue: 'rgb(54, 162, 235)'
    };
    
    var color = Chart.helpers.color;
    var config = {
        type: 'bar',
        data: {
            datasets: [{
                data: dataFromServer,
                type: 'line',
                yAxisID: 'temperature',
                backgroundColor: 'transparent',
                borderColor: chartColors.red,
                pointBackgroundColor: chartColors.red,
                tension: 0,
                fill: false
            }, {
                yAxisID: 'precipitation',
                backgroundColor: color(chartColors.blue).alpha(0.5).rgbString(),
                borderColor: 'transparent'
            }]
        },
        // plugins: [ChartDataSource],
        options: {
            title: {
                display: true,
                text: 'JSON data source (dataset) sample'
            },
            scales: {
                xAxes: [{
                    scaleLabel: {
                        display: true,
                        labelString: 'Month'
                    }
                }],
                yAxes: [{
                    id: 'temperature',
                    gridLines: {
                        drawOnChartArea: false
                    },
                    scaleLabel: {
                        display: true,
                        labelString: 'Temperature (°C)'
                    }
                }, {
                    id: 'precipitation',
                    position: 'right',
                    gridLines: {
                        drawOnChartArea: false
                    },
                    scaleLabel: {
                        display: true,
                        labelString: 'Precipitation (mm)'
                    }
                }]
            },
            // plugins: {
            //     datasource: {
            //         data: dataFromServer,
            //         type: 'json',
            //         rowMapping: 'dataset',
            //         indexLabels: 'labels',
            //         datasetLabels: 'datasets[*].label',
            //         data: 'datasets[*].data'
            //     }
            // }
        }
    };
    
    }

    


    $("#line").unbind('click').click(function () {
        console.log("inside lineListener")
        change('line');
    });

    $("#bar").unbind('click').click(function () {
        console.log("inside barListener")
        change('bar');
    });

    var count = 0

    function change(newType) {
        if(loading)
        return
        // var ctx = document.getElementById("canvas").getContext("2d");
        const canvas = document.getElementById('canvas');
        const ctx = canvas.getContext('2d');


        // Remove the old chart and all its event handles

        if (myChart) {
            console.log("chart is getting destroyed" + count);
            count = count + 1;
            myChart.destroy();
        }

        // Chart.js modifies the object you pass in. Pass a copy of the object so we can use the original object later
        console.log("before change " + myChart)
        var temp = jQuery.extend(true, {}, config);

        temp.type = newType;
        myChart = new Chart(ctx, temp);
        myChart.resize(600, 600)
        //myChart.options.plugins.legend.position = 'top';
        //myChart.options.plugins.title.text = globalTitle;
        if(dataFromServer !== undefined){
            myChart.data = dataFromServer;
        }
        myChart.update();

        console.log("after change " + myChart)
    }
    var globalTitle;
    function changeName(){
        if(loading)
        return;
        console.log("inside change name");
        var title = document.getElementById("title").value;
        console.log(title);
        myChart.options.plugins.title.text = title;
        myChart.update();
        globalTitle = title;
    }

    // document.getElementById("myBtn").click();

    function getChart(){
        if(!loading){
            //console.log("Get chart data" + dataFromServer)
            return(
                <div className={"chartPosition"}>
            <button id='line'>line</button>
            <button id='bar'>bar</button>
            <canvas id="canvas"></canvas>
            <div>
                <button type="submit" form="form1" value="Submit" onClick={changeName}>Submit</button>
                <label className={"pad"} htmlFor="fname"> Enter title:</label>
                <input type="text" id="title" name="title" color={"white"}/>
            </div>

        </div>
            )
        }
        else return(
            <h1>LOADING</h1>
        )
    }

    return (
        <div>{getChart()}</div>
    )

}



