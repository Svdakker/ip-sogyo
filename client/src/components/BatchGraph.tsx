import {Model} from "../Types.tsx";
import {Line} from "react-chartjs-2";

export const BatchGraph = ( { data }: Model) => {

    const timePoints = () => data?.map(dataPoint => dataPoint.time)
    const cellDensities = () => data?.map(dataPoint => dataPoint.cellDensity)
    const sugarConcentrations = () => data?.map(dataPoint => dataPoint.sugarConcentration)

    const canvasData = {
        datasets: [
            {
                label: "cell density",
                data: cellDensities()
            },
            {
                label: "sugar concentration",
                data: sugarConcentrations()
            }
        ]
    }

    const options = {
        scales: {
            x: {
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
                text: "Batch operation modeled over time"
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
        <div>
            <Line id={"batch-cultivation"} options={options} data={canvasData}/>
        </div>
    )
}