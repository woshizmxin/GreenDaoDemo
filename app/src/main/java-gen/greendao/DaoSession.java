package greendao;

import android.database.sqlite.SQLiteDatabase;

import java.util.Map;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.AbstractDaoSession;
import de.greenrobot.dao.identityscope.IdentityScopeType;
import de.greenrobot.dao.internal.DaoConfig;

import greendao.Candidate;

import greendao.CandidateDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see de.greenrobot.dao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig candidateDaoConfig;

    private final CandidateDao candidateDao;

    public DaoSession(SQLiteDatabase db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        candidateDaoConfig = daoConfigMap.get(CandidateDao.class).clone();
        candidateDaoConfig.initIdentityScope(type);

        candidateDao = new CandidateDao(candidateDaoConfig, this);

        registerDao(Candidate.class, candidateDao);
    }
    
    public void clear() {
        candidateDaoConfig.getIdentityScope().clear();
    }

    public CandidateDao getCandidateDao() {
        return candidateDao;
    }

}
