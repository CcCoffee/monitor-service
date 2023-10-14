// RuleModal.js
import React, { useEffect, useState } from 'react';
import { Modal, Form, Button } from 'react-bootstrap';
import Select from 'react-select';

const RuleModal = ({
  showModal,
  handleCloseModal,
  handleSaveRule,
  rule,
  handleRuleInputChange,
}) => {
  const [linkedServerOptions, setLinkedServerOptions] = useState([]);
  const [selectedServerOptions, setSelectedServerOptions] = useState([]);

  const [linkedChannelOptions, setLinkedChannelOptions] = useState([]);
  const [selectedChannelOptions, setSelectedChannelOptions] = useState([]);

  const generateServerOptions = () => {
    return fetch('http://localhost:8090/servers/all')
      .then(response => response.json())
      .then(data => {
        if (data.code === 200) {
          const options = data.data.map(item => ({
            label: item.serverName,
            value: item.id,
          }));
          setLinkedServerOptions(options);
          return options;
        } else {
          console.error(data.message);
          // TODO: 处理保存失败的情况，如显示错误消息等
          return [];
        }
      })
      .catch(error => {
        console.error("An error occurred while generating server options:", error);
        // TODO: 可以执行其他操作，如显示错误消息等
      });
  };

  const generateChannelOptions = () => {
    return fetch('http://localhost:8090/channels/all')
      .then(response => response.json())
      .then(data => {
        if (data.code === 200) {
          const options = data.data.map(item => ({
            label: item.name,
            value: item.id,
          }));
          setLinkedChannelOptions(options);
          return options;
        } else {
          console.error(data.message);
          // TODO: 处理保存失败的情况，如显示错误消息等
          return [];
        }
      })
      .catch(error => {
        console.error("An error occurred while generating server options:", error);
        // TODO: 可以执行其他操作，如显示错误消息等
      });
  };

  const querySelectedServerOptions = () => {
    if(rule.id) {
        fetch(`http://localhost:8090/servers/all?ruleId=${rule.id}`)
        .then(response => response.json())
        .then(data => {
          if (data.code === 200) {
            const options = data.data.map(item => ({
              label: item.serverName,
              value: item.id,
              }));
            setSelectedServerOptions(options);
            handleRuleInputChange({target: {name: 'linkedServers', value: options.map(option=>{
              return {id: option.value}
            })}})
          } else {
            console.error(data.message);
            // TODO: 处理保存失败的情况，如显示错误消息等
            return [];
          }
        })
        .catch(error => {
          console.error("An error occurred while querying selected server options:", error);
          // TODO: 可以执行其他操作，如显示错误消息等
        });
    }
  }

  const querySelectedChannelOptions = () => {
    if(rule.id) {
        fetch(`http://localhost:8090/channels/all?ruleId=${rule.id}`)
        .then(response => response.json())
        .then(data => {
          if (data.code === 200) {
            const options = data.data.map(item => ({
              label: item.name,
              value: item.id,
              }));
            setSelectedChannelOptions(options);
            handleRuleInputChange({target: {name: 'channelIds', value: options.map(option=>{
              return option.value
            })}})
          } else {
            console.error(data.message);
            // TODO: 处理保存失败的情况，如显示错误消息等
            return [];
          }
        })
        .catch(error => {
          console.error("An error occurred while querying selected channel options:", error);
          // TODO: 可以执行其他操作，如显示错误消息等
        });
    }
  }

  useEffect(() => {
    if(showModal) {
        generateServerOptions();
        querySelectedServerOptions();
        generateChannelOptions();
        querySelectedChannelOptions();
    }
  }, [showModal]);

  const handleSelectServerChange = (selectedOptions) => {
    console.log("handleSelectChange", selectedOptions)
    setSelectedServerOptions(selectedOptions);
    handleRuleInputChange({target: {name: 'linkedServers', value: selectedOptions.map(option=>{
        return {id: option.value}
    })}})
  };

  const handleSelectChannelChange = (selectedOptions) => {
    console.log("handleSelectChange", selectedOptions)
    setSelectedChannelOptions(selectedOptions);
    handleRuleInputChange({target: {name: 'channelIds', value: selectedOptions.map(option=>{
        return option.value
    })}})
  };
      
  return (
    <Modal show={showModal} onHide={handleCloseModal}>
      <Modal.Header closeButton>
        <Modal.Title>{rule.id ? 'Edit Rule' : 'Insert Rule'}</Modal.Title>
      </Modal.Header>
      <Modal.Body>
        <Form>
            <Form.Group controlId="newRuleName" className='mb-1'>
              <Form.Label>Name</Form.Label>
              <Form.Control
                type="text"
                placeholder="Input name"
                name="name"
                defaultValue={rule.name}
                onChange={handleRuleInputChange}
                required
              />
            </Form.Group>
            <Form.Group controlId="newRuleType" className='mb-1'>
              <Form.Label>Type</Form.Label>
              <Form.Select
                name="type"
                defaultValue={rule.type}
                onChange={handleRuleInputChange}
                required
              >
                <option value="">Please select</option>
                <option value="PROCESS">Process</option>
                <option value="LOG">Log</option>
              </Form.Select>
            </Form.Group>
            <Form.Group controlId="newRuleDescription" className='mb-1'>
              <Form.Label>Description</Form.Label>
              <Form.Control
                type="text"
                placeholder="Enter description"
                name="description"
                defaultValue={rule.description}
                onChange={handleRuleInputChange}
                required
              />
            </Form.Group>
            <Form.Group controlId="newRuleEnabled" className='mb-1'>
              <Form.Label>Enabled</Form.Label>
              <Form.Select
                label="Enabled"
                name="enabled"
                onChange={handleRuleInputChange}
                required
                defaultValue={rule.enabled}
              >
                <option value={true}>True</option>
                <option value={false}>False</option>
              </Form.Select>
            </Form.Group>
            <Form.Group controlId="newRuleThreshold" className='mb-1'>
              <Form.Label>Threshold</Form.Label>
              <Form.Control
                type="number"
                placeholder="Enter threshold"
                name="threshold"
                defaultValue={rule.threshold}
                onChange={handleRuleInputChange}
              />
            </Form.Group>
            <Form.Group controlId="newRuleInterval" className='mb-1'>
              <Form.Label>Interval</Form.Label>
              <Form.Control
                type="number"
                placeholder="Enter interval"
                name="interval"
                defaultValue={rule.interval}
                onChange={handleRuleInputChange}
              />
            </Form.Group>
            <Form.Group controlId="newRuleNotificationRecipients" className='mb-1'>
              <Form.Label>Notification Recipients</Form.Label>
              <Form.Control
                as="textarea" rows={3}
                placeholder="Enter notification recipients, splited by new line"
                name="notificationRecipients"
                defaultValue={rule.notificationRecipients?rule.notificationRecipients.join("\n"):""}
                onChange={handleRuleInputChange}
                required
              />
            </Form.Group>
            <Form.Group controlId="newRuleApplication" className='mb-1'>
              <Form.Label>Application Name</Form.Label>
              <Form.Control
                type="text"
                placeholder="Enter application name"
                name="application"
                defaultValue={rule.application}
                onChange={handleRuleInputChange}
              />
            </Form.Group>
            <Form.Group controlId="newRuleProcessNameRegex" className='mb-1'>
              <Form.Label>Process Name Regex</Form.Label>
              <Form.Control
                type="text"
                placeholder="Enter process name regex"
                name="processNameRegex"
                defaultValue={rule.processNameRegex}
                onChange={handleRuleInputChange}
              />
            </Form.Group>
            <Form.Group controlId="newRuleLogFilePath" className='mb-1'>
              <Form.Label>Log File Path</Form.Label>
              <Form.Control
                type="text"
                placeholder="Enter log file path"
                name="logFilePath"
                defaultValue={rule.logFilePath}
                onChange={handleRuleInputChange}
              />
            </Form.Group>
            <Form.Group controlId="newRuleLogPatterns" className='mb-1'>
              <Form.Label>Log Patterns</Form.Label>
              <Form.Control
                as="textarea" rows={3}
                placeholder="Enter log patterns, splited by new line"
                name="logPatterns"
                defaultValue={rule.logPatterns?rule.logPatterns.join("\n"):""}
                onChange={handleRuleInputChange}
              />
            </Form.Group>
            <Form.Group controlId="newRuleServers" className='mb-1'>
              <Form.Label>Linked servers</Form.Label>
              <Select
                isMulti
                options={linkedServerOptions}
                value={selectedServerOptions}
                onChange={handleSelectServerChange}
                placeholder="Please select"
                isSearchable
                required
                />
            </Form.Group>
            <Form.Group controlId="newRuleChannels" className='mb-1'>
              <Form.Label>Notification channels</Form.Label>
              <Select
                isMulti
                options={linkedChannelOptions}
                value={selectedChannelOptions}
                onChange={handleSelectChannelChange}
                placeholder="Please select"
                isSearchable
                required
                />
            </Form.Group>
          </Form>
      </Modal.Body>
      <Modal.Footer>
        <Button variant="secondary" onClick={handleCloseModal}>
          Cancel
        </Button>
        <Button type="submit" variant="primary" onClick={()=>handleSaveRule(rule)}>
          Save
        </Button>
      </Modal.Footer>
    </Modal>
  );
};

export default RuleModal;
