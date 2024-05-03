import {TableData} from "../ResultTypes.tsx";

export const ResultTable = ( { data, switchGraph }: TableData ) => {
    return (
        <>
            <table className="w-full my-6 mx-8 text-sm text-left rtl:text-right text-gray-400">
                <thead className="text-xs uppercase bg-gray-700 text-gray-400">
                <tr>
                    <th scope="col" className="px-6 py-3">
                        Operation
                    </th>
                    <th scope="col" className="px-6 py-3">
                        Duration (h)
                    </th>
                    <th scope="col" className="px-6 py-3">
                        Energy costs (€)
                    </th>
                    <th scope="col" className="px-6 py-3">
                        Energy Used (kWh)
                    </th>
                </tr>
                </thead>
                <tbody>
                {fillBody()}
                </tbody>
            </table>
        </>
    )

    function fillBody() {
        return data.map((batchCultivation, index) => {
            return (
                <tr key={index} className="border-b bg-gray-800 border-gray-700">
                    <th scope="row" onClick={() => switchGraph(index)} className="px-6 py-4 font-medium whitespace-nowrap text-white hover:cursor-pointer">
                        {`Batch cultivation ${index + 1}`}
                    </th>
                    <td className="px-6 py-4">
                        {String(batchCultivation.duration)}
                    </td>
                    <td className="px-6 py-4">
                        {String(batchCultivation.costEstimation.energy)}
                    </td>
                    <td className="px-6 py-4">
                        {String(batchCultivation.powerConsumption.operations)}
                    </td>
                </tr>
            )
        })
    }
}