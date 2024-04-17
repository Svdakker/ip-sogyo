import {Model} from "../Types.tsx";
import {Line} from "react-chartjs-2";

export const BatchGraph = ( { data }: Model) => {

    const timePoints = () => data?.map(dataPoint => dataPoint.time)
    const cellDensities = () => data?.map(dataPoint => dataPoint.cellDensity)
    const sugarConcentrations = () => data?.map(dataPoint => dataPoint.sugarConcentration)

    const canvasData = {
        labels: timePoints(),
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
        plugins: {
            title: {
                display: true,
                    text: "Batch operation modeled over time"
            },
            legend: {
                display: false
            }
        }
    }

    const graphStyle = {

    }

    return (
        <div style={graphStyle}>
            <Line id={"batch"} options={options} data={canvasData}/>
        </div>
    )
}