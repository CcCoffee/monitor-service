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

    const generateServerOptions = () => {
        return fetch('http://localhost:8090/servers')
          .then(response => response.json())
          .then(data => {
            const options = data.map(item => ({
              label: item.serverName,
              value: item.id,
            }));
            setLinkedServerOptions(options);
            return options;
          })
          .catch(error => {
            console.error(error);
          });
      };

      const querySelectedServerOptions = () => {
        if(rule.id) {
            fetch(`http://localhost:8090/servers?ruleId=${rule.id}`)
            .then(response => response.json())
            .then(data => {
              const options = data.map(item => ({
                label: item.serverName,
                value: item.id,
                }));
              setSelectedServerOptions(options);
              handleRuleInputChange({target: {name: 'linkedServers', value: options.map(option=>{
                return {id: option.value}
            })}})
            })
            .catch(error => console.error(error));
        }
      }

      useEffect(() => {
        if(showModal) {
            generateServerOptions();
            querySelectedServerOptions();
        }
      }, [showModal]);

    
      const handleSelectChange = (selectedOptions) => {
        console.log("handleSelectChange", selectedOptions)
        setSelectedServerOptions(selectedOptions);
        handleRuleInputChange({target: {name: 'linkedServers', value: selectedOptions.map(option=>{
            return {id: option.value}
        })}})
      };

      
  return (
    <Modal show={showModal} onHide={handleCloseModal}>
      <Modal.Header closeButton>
        <Modal.Title>{rule.id ? '编辑 Rule' : '新增 Rule'}</Modal.Title>
      </Modal.Header>
      <Modal.Body>
        <Form>
            <Form.Group controlId="newRuleName" className='mb-1'>
              <Form.Label>Name</Form.Label>
              <Form.Control
                type="text"
                placeholder="输入名称"
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
                <option value="">选择类型</option>
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
                onChange={handleSelectChange}
                placeholder="选择选项..."
                isSearchable
                />
            </Form.Group>
          </Form>
      </Modal.Body>
      <Modal.Footer>
        <Button variant="secondary" onClick={handleCloseModal}>
          取消
        </Button>
        <Button type="submit" variant="primary" onClick={()=>handleSaveRule(rule)}>
          保存
        </Button>
      </Modal.Footer>
    </Modal>
  );
};

export default RuleModal;
