import {GraphData} from "../ResultTypes.tsx";
import {Line} from "react-chartjs-2";

export const BatchGraph = ( { position, model }: GraphData) => {

    const timePoints = () => model?.map(dataPoint => dataPoint[0])
    const cellDensities = () => model?.map(dataPoint => dataPoint[1])
    const sugarConcentrations = () => model?.map(dataPoint => dataPoint[2])

    const canvasData = {
        datasets: [
            {
                label: "Cells",
                data: cellDensities()
            },
            {
                label: "Sugar",
                data: sugarConcentrations()
            }
        ]
    }

    const options = {
        scales: {
            x: {
                title: {
                  display: true,
                  text: "Time (h)",
                  color: "rgb(255,255,255)",
                  font: {
                      size: 16,
                  },
                },
                grid: {
                    color: "rgba(255,255,255,0.1)",
                },
                labels: timePoints()?.map(dataPoint => dataPoint.toFixed(2)),
                ticks: {
                    color: "rgb(255,255,255)",
                    font: {
                        size: 12,
                    },
                },
            },
            y: {
                title: {
                    display: true,
                    text: "Concentration (g/L)",
                    color: "rgb(255,255,255)",
                    font: {
                        size: 16,
                    },
                },
                grid: {
                    color: "rgba(255,255,255,0.1)",
                },
                ticks: {
                    color: "rgb(255,255,255)",
                    font: {
                        size: 12,
                    },
                },
            },
        },
        plugins: {
            title: {
                font: {
                    size: 28,
                },
                color: "rgb(255,255,255)",
                display: true,
                text: `Batch operation ${position} modeled over time`
            },
            legend: {
                labels: {
                    color: 'rgb(255,251,251)'
                },
                display: true,
            }
        }
    }

    return (
        <>
            <Line options={options} data={canvasData}/>
        </>
    )
}