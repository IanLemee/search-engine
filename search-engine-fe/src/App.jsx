import {useState} from 'react'
import { getCompletions, search } from "./Api.js";
import { Input, InputGroup, List} from 'rsuite';
import SearchIcon from '@rsuite/icons/Search';
import 'rsuite/Button/styles/index.css';


function App() {
  const [results, setResults] = useState([]);
  const [pref, setPref] = useState("");

  const handleCompletions = async (prefix) => {
    if (prefix.trim() === "") {
      setPref("");
      setResults([]);
      return;
    }
    const search = await getCompletions(prefix);
    setPref(prefix);
    setResults(search);
  }
  const handleSearch = () => {
    if(pref.trim() === "") return;

    search(pref);
    setPref("");
    setResults([]);
  }
  const listOfResults = results.map(res => {return <List.Item>{res}</List.Item>});

  return (
    <>
      <div>
        <h2>Search Engine</h2>
      </div>

      <div>
        <InputGroup size="lg" className="rsuite-search">
          <Input value={pref} onInput={event => handleCompletions(event.target.value)}/>


          <InputGroup.Button onClick={handleSearch}>
            <SearchIcon />
          </InputGroup.Button>

        </InputGroup>

        {results.length > 0 && (
            <List bordered>
              {listOfResults}
            </List>
        )}
      </div>
    </>
  )
}

export default App
