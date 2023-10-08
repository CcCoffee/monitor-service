import React, { useState } from 'react';
import Select from 'react-select';

function MultiSelectWithSearch() {
  const options = [
    { value: 'option1', label: '选项 11' },
    { value: 'option2', label: '选项 2' },
    { value: 'option3', label: '选项 3' },
    // 添加更多选项...
  ];

  const [selectedOptions, setSelectedOptions] = useState([]);

  const handleSelectChange = (selectedOptions) => {
    setSelectedOptions(selectedOptions);
  };

  return (
    <Select
      isMulti
      options={options}
      value={selectedOptions}
      onChange={handleSelectChange}
      placeholder="选择选项..."
      isSearchable
    />
  );
}

export default MultiSelectWithSearch;