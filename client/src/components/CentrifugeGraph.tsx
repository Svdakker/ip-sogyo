import {GraphData} from "../ResultTypes.tsx";
import {Line} from "react-chartjs-2";

export const CentrifugeGraph = ( { position, model }: GraphData) => {

    const flowRates = () => model?.map(dataPoint => dataPoint[0])
    const efficiency = () => model?.map(dataPoint => dataPoint[1])
    const duration = () => model?.map(dataPoint => dataPoint[2])
    const energy = () => model?.map(dataPoint => dataPoint[3])
    const costs = () => model?.map(dataPoint => dataPoint[4])

    const right = "right" as const
    const left = "left" as const

    const canvasData = {
        datasets: [
            {
                label: "Efficiency of separation",
                data: efficiency(),
                yAxisID: 'y'
            },
            {
                label: "Duration",
                data: duration(),
                yAxisID: 'y1'
            },
            {
                label: "Energy costs",
                data: energy(),
                yAxisID: 'y2'
            },
            {
                label: "Costs",
                data: costs(),
                yAxisID: 'y3'
            }
        ]
    }

    const options = {
        scales: {
            x: {
                title: {
                    display: true,
                    text: "Flow rate (L s-1)",
                    color: "rgb(255,255,255)",
                    font: {
                        size: 16,
                    },
                },
                grid: {
                    color: "rgba(255,255,255,0.1)",
                },
                labels: flowRates()?.map(dataPoint => (dataPoint * 1000).toFixed(2)),
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
                    text: "Efficiency of separation",
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
                position:left,
            },
            y1: {
                title: {
                    display: true,
                    text: "Duration (h)",
                    color: "rgb(255,255,255)",
                    font: {
                        size: 16,
                    },
                },
                grid: {
                    drawOnChartArea: false,
                    color: "rgba(255,255,255,0.1)",
                },
                ticks: {
                    color: "rgb(255,255,255)",
                    font: {
                        size: 12,
                    },
                },
                position:left,
            },
            y2: {
                title: {
                    display: true,
                    text: "Energy consumption (kWh)",
                    color: "rgb(255,255,255)",
                    font: {
                        size: 16,
                    },
                },
                grid: {
                    drawOnChartArea: false,
                    color: "rgba(255,255,255,0.1)",
                },
                ticks: {
                    color: "rgb(255,255,255)",
                    font: {
                        size: 12,
                    },
                },
                position: right
            },
            y3: {
                title: {
                    display: true,
                    text: "Costs (Euro)",
                    color: "rgb(255,255,255)",
                    font: {
                        size: 16,
                    },
                },
                grid: {
                    drawOnChartArea: false,
                    color: "rgba(255,255,255,0.1)",
                },
                ticks: {
                    color: "rgb(255,255,255)",
                    font: {
                        size: 12,
                    },
                },
                position: right,
            }
        },
        plugins: {
            title: {
                font: {
                    size: 28,
                },
                color: "rgb(255,255,255)",
                display: true,
                text: `Centrifugation operation ${position} modeled over time`
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