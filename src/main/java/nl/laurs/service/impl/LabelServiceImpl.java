package nl.laurs.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import nl.laurs.persistance.LabelDao;
import nl.laurs.domain.Label;
import nl.laurs.service.LabelService;

/**
 * @author: ML
 */
@Service
@Transactional(readOnly = true)
public class LabelServiceImpl extends AbstractGenericEntityServiceImpl<Label> implements LabelService {

    @Autowired
    public void setEntityDao(LabelDao labelDao) {
        super.setEntityDao(labelDao);
    }

    private LabelDao getLabelDao() {
        return (LabelDao) entityDao;
    }
    public Label getByDiscogsId(Integer discogsId) {
        return getLabelDao().getByDiscogsId(discogsId);
    }
}
