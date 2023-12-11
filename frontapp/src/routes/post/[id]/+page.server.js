export const load = async (serverLoadEvent) => {
    const {fetch,params} = serverLoadEvent;
    const {id} = params;
    return {
        id
    }
}