const URL = 'http://localhost:8080/search';

export async function getCompletions(prefix) {
    console.log(prefix)
    const PATH = `${URL}/${prefix.trim()}`;
    try {
        const data = await fetch(PATH, {
            method: 'GET'
        });
        if (!data || data.length <= 0 || data.status !== 200) {
            return [];
        }

        return data.json()
    } catch (error) {
        console.error(error);
        return [];
    }
}

export function search(searchText) {
    const PATH = `${URL}`;
    const BODY = {
        prefix: searchText
    }

    try {
        fetch(PATH, {
            method: 'POST',
            body: JSON.stringify(BODY),
            headers: {
                'Content-Type': 'application/json'
            }
        });
    } catch (error) {
        throw new Error(error, { cause: error })
    }
}