import {TableData} from "../Types.tsx";

export const ResultTable = ( { duration }: TableData) => {
    return (
        <>
            <table className="m-3 text-sm text-left rtl:text-right text-gray-400">
                <thead className="text-xs uppercase bg-gray-700 text-gray-400">
                <tr>
                    <th scope="col" className="px-6 py-3">
                        Operation
                    </th>
                    <th scope="col" className="px-6 py-3">
                        Duration
                    </th>
                </tr>
                </thead>
                <tbody>
                <tr className="border-b bg-gray-800 border-gray-700">
                    <th scope="row" className="px-6 py-4 font-medium whitespace-nowrap text-white">
                        Batch cultivation
                    </th>
                    <td className="px-6 py-4">
                        {String(duration)}
                    </td>
                </tr>
                </tbody>
            </table>
        </>
    )
}